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
    // 由 CreateDeviceSceneModel 传入，双击时随 cellInfo 一起给窗口
    calculateType: 0,

    cs: null,
    labelLayer: null,
    imageUrl: '',
    cachedOrigin: { x: 0, y: 0, scale: 1, ready: false },
    bindingMap: null,
    // deviceId → Set<uuid>，加速 WebSocket 推送时的设备命中与批量更新
    deviceIndexMap: null,
    labelStyleMap: null,
    userColorMap: null,
    userLineWidthMap: null,

    // 报警配色（来自后端 alarmShowStyle）
    alarmShowStyle: null,

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
    _resizing: false,
    _resizeTimer: null,
    _originTimer: null,
    _visibilityHandler: null,

    // ★ 新增：请求序号，用于过滤过期的 AJAX 响应
    _loadSceneSeq: 0,

    // ★ 新增：组件是否已销毁，阻止异步回调再操作已销毁的组件
    _destroyed: false,

    BLANK_IMAGE: context + '/sceneModelController/getSceneImage?name=__blank__.svg&_v=2',

    // ========================================================
    // 工具：统一的安全调用包装
    // ========================================================
    safe: function(fn, label) {
        try { return fn(); }
        catch (e) {
            if (label) console.warn('[RealtimeSceneModelPanel] ' + label + ':', e);
        }
    },

    // ========================================================
    // 工具：统一获取真实的 canvas DOM
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
    // 工具：origin 轮询定时器的启动/停止
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
    // 工具：清空所有绑定相关 Map
    //   —— 统一出口，避免重复渲染时脏数据残留
    // ========================================================
    _clearAllBindingMaps: function() {
        var me = this;
        if (me.bindingMap)        me.bindingMap.clear();
        if (me.deviceIndexMap)    me.deviceIndexMap.clear();
        if (me.labelStyleMap)     me.labelStyleMap.clear();
        if (me.userColorMap)      me.userColorMap.clear();
        if (me.userLineWidthMap)  me.userLineWidthMap.clear();
    },

    // ========================================================
    // 关键：主动派发一次"鼠标移到画布中心"的合成 mousemove
    // ========================================================
    _nudgeCanvasMouseCenter: function() {
        var me = this;
        if (me._destroyed) return;
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
        } catch (e) {
            // 兼容旧 IE：尝试 initMouseEvent
            try {
                ev = document.createEvent('MouseEvents');
                ev.initMouseEvent('mousemove', true, true, window,
                    0, 0, 0, clientX, clientY, false, false, false, false, 0, null);
            } catch (e2) { return; }
        }

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
        me.deviceIndexMap = new Map();
        me.labelStyleMap = new Map();
        me.userColorMap = new Map();
        me.userLineWidthMap = new Map();

        Ext.apply(me, {
            id: 'RealTimeMonitoringSceneModelPanel_Id',
            tbar: [{
            	xtype: 'label',
            	html: '双击标注查看曲线或数据',
            	style: {
                    marginLeft: '10px',
                    fontSize: '11px',
                    fontFamily: 'tahoma,arial,verdana,sans-serif',
                    color: '#333333'
                }
            },'->',
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
                if (me._destroyed) return;
                me.initCanvasSelect();
                me.loadSceneConfigByDevice(me.deviceId);
            }, 100, me);
        });

        // 用 mon 让销毁时自动清理
        me.mon(me, 'resize', function() {
            if (me._destroyed) return;
            if (me._resizeTimer) clearTimeout(me._resizeTimer);
            me._resizeTimer = setTimeout(function() {
                if (me._destroyed) return;
                me.safe(function() { me.handleResize(); }, 'handleResize');
            }, 150);
        });
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

            // 同步绘图缓冲尺寸，避免拉伸变形
            if (dom.tagName === 'CANVAS') {
                if (dom.width  !== cssW) dom.width  = cssW;
                if (dom.height !== cssH) dom.height = cssH;
            }

            if (typeof me.cs.resize === 'function') me.cs.resize();
            if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom();

            // resize 后 nudge 一次，确保 origin 依赖字段已初始化
            me._nudgeCanvasMouseCenter();

            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        } catch (e) {
            console.warn('[RealtimeSceneModelPanel] handleResize 失败:', e);
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
    // 工具：从 canvas-select 的 info / shape 中取 uuid
    // ========================================================
    extractUuid: function(info) {
        if (!info) return null;
        if (info.uuid) return info.uuid;
        if (info.index !== undefined && this.cs && this.cs.dataset && this.cs.dataset[info.index]) {
            return this.cs.dataset[info.index].uuid;
        }
        return null;
    },

    // ========================================================
    // 初始化 canvas-select
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

        me.enforceReadOnly();

        me.cs.on('load', function() {
            if (me._destroyed) return;
            try { if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom(); }
            catch (e) { console.warn('[RealtimeSceneModelPanel] load fitZoom 失败', e); }

            me.enforceReadOnly();

            // 让库内部完成 mouse/position 初始化
            me._nudgeCanvasMouseCenter();

            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();

            // 兜底：延时再适应一次，应对异步渲染
            Ext.defer(function() {
                if (me._destroyed) return;
                if (!me.cs) return;
                me._nudgeCanvasMouseCenter();
                me.updateOriginIfNeeded(true);
                me.scheduleRenderLabels();
            }, 150, me);
        });

        me.cs.on('updated', function() { if (me._destroyed) return; me.scheduleRenderLabels(); });
        me.cs.on('coor',    function() { if (me._destroyed) return; me.scheduleRenderLabels(); });

        // 图形双击 → 打开历史数据 / 历史曲线
        canvasEl.addEventListener('dblclick', function(e) {
            if (me._destroyed) return;
            if (!me.cs) return;
            var info = me.cs.activeShape;
            if (!info) return;

            var uuid = me.extractUuid(info);
            if (!uuid) return;

            var binding = me.bindingMap.get(uuid);
            if (!binding) return;

            e.stopPropagation();
            e.preventDefault();
            try { me.openBindingItemHistory(binding); }
            catch (err) { console.warn('[RealtimeSceneModelPanel] 图形双击打开历史失败', err); }
        });

        me.startOriginTimer();

        // 页面可见性监听
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
    // 加载场景配置
    //
    //   ★ 竞态防护：用 _loadSceneSeq 过滤过期响应
    //     快速切换设备时，旧请求的响应到达后会被丢弃，
    //     不会再覆盖新设备的配置
    // ========================================================
    loadSceneConfigByDevice: function(deviceId) {
        var me = this;
        if (me._destroyed) return;

        if (!deviceId || deviceId <= 0) {
            me.renderSceneConfig({ imageUrl: '', shapes: [] });
            return;
        }

        // ★ 生成本次请求的唯一序号
        var seq = ++me._loadSceneSeq;

        Ext.Ajax.request({
            url: context + '/realTimeMonitoringController/getSceneModelConfigWithRealtimeValues',
            method: 'POST',
            params: { deviceId: deviceId },
            success: function(resp) {
                // ★ 忽略过期响应（用户已切换到其他设备）
                if (seq !== me._loadSceneSeq) return;
                if (me._destroyed) return;

                try {
                    var result = Ext.decode(resp.responseText);

                    if (result.alarmShowStyle) {
                        try {
                            me.alarmShowStyle = (typeof result.alarmShowStyle === 'string')
                                ? Ext.decode(result.alarmShowStyle)
                                : result.alarmShowStyle;
                        } catch (e) {
                            me.alarmShowStyle = null;
                        }
                    } else {
                        me.alarmShowStyle = null;
                    }

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
                // ★ 忽略过期响应
                if (seq !== me._loadSceneSeq) return;
                if (me._destroyed) return;
                me.renderSceneConfig({ imageUrl: '', shapes: [] });
            }
        });
    },

    // ========================================================
    // 图片加载
    // ========================================================
    setCanvasImage: function(url) {
        var me = this;
        if (me._destroyed) return;
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
            if (me._destroyed) return;
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
            if (me._destroyed) return;
            me._settingImage = false;
            me.imageUrl = '';
            me._canvasCurrentUrl = null;
            me.rebuildCanvasSelect();
        };
        probe.src = url;
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
    // 用 config 渲染
    //
    //   ★ 关键修复1：开头清空所有绑定相关 Map，
    //     避免跨设备切换 / 重复渲染时脏数据残留
    //   ★ 关键修复2：binding.deviceId 统一转为 String
    //   ★ 关键修复3：整体 try/catch 兜底，任何环节异常
    //     都能保证 5 个 Map 至少处于"可用空状态"，
    //     不至于让后续推送和标签渲染彻底失效
    // ========================================================
    renderSceneConfig: function(cfg) {
        var me = this;
        if (me._destroyed) return;
        if (!cfg) cfg = { imageUrl: '', shapes: [] };

        try {
            if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
            if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

            // ★★★ 关键修复：清空所有绑定相关 Map
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
            }

            if (cfg.imageUrl) me.setCanvasImage(cfg.imageUrl);
            else me.setCanvasImage(me.BLANK_IMAGE);

            var shapes = cfg.shapes || [];
            shapes.forEach(function(s) {
                me.userColorMap.set(s.uuid, s.strokeStyle || '#00FF00');
                me.userLineWidthMap.set(s.uuid, s.lineWidth != null ? s.lineWidth : 1);

                if (s.deviceId && s.fieldName) {
                    // ★ 统一将 deviceId 转为字符串
                    var devKey = String(s.deviceId);

                    me.bindingMap.set(s.uuid, {
                        deviceId:     devKey,
                        deviceName:   s.deviceName,
                        fieldName:    s.fieldName,
                        fieldLabel:   s.fieldLabel,
                        unit:         s.unit,
                        dataSource:   s.dataSource,
                        currentValue: s.currentValue,
                        alarmLevel:   (s.alarmLevel != null) ? s.alarmLevel : 0,
                        commStatus:   (s.commStatus != null) ? s.commStatus : 0,

                        itemType:       s.itemType,
                        resolutionMode: s.resolutionMode,
                        bitIndex:       s.bitIndex,
                        columnDataType: s.columnDataType
                    });

                    // ★ 同步写入索引：deviceId → Set<uuid>
                    var uuidSet = me.deviceIndexMap.get(devKey);
                    if (!uuidSet) {
                        uuidSet = new Set();
                        me.deviceIndexMap.set(devKey, uuidSet);
                    }
                    uuidSet.add(s.uuid);
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
            // ★ 兜底：任何异常都要保证 5 个 Map 处于"可用空状态"，
            //    否则后续标签渲染和 WebSocket 推送会彻底失效
            console.error('[RealtimeSceneModelPanel] renderSceneConfig 异常:', e);

            if (!me.bindingMap)        me.bindingMap = new Map();
            if (!me.deviceIndexMap)    me.deviceIndexMap = new Map();
            if (!me.labelStyleMap)     me.labelStyleMap = new Map();
            if (!me.userColorMap)      me.userColorMap = new Map();
            if (!me.userLineWidthMap)  me.userLineWidthMap = new Map();

            // 尝试清空一次，至少保持 UI 干净
            try { me._clearAllBindingMaps(); } catch (e2) {}
        }
    },

    // ========================================================
    // safeSetData：用完整 JSON 比较代替"只比 uuid"
    // ========================================================
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
    // origin 计算
    //
    //   ★ 加固：增加长度检查 + isFinite 校验，
    //     防止 canvas-select 内部字段异常导致 origin 变成 NaN
    // ========================================================
    updateOriginIfNeeded: function(force) {
        if (this._destroyed) return false;
        if (!this.cs) return false;
        var cs = this.cs;
        var scale = cs.scale || 1;

        // ★ 长度检查：防止字段非数组时访问 [0]/[1] 得 undefined
        if (!cs.mouse || !cs.position
            || !cs.mouse.length || cs.mouse.length < 2
            || !cs.position.length || cs.position.length < 2) {
            return false;
        }

        var mx = cs.mouse[0], my = cs.mouse[1];
        var px = cs.position[0], py = cs.position[1];

        // ★ isFinite 校验：任一值不是有限数 → 跳过本次计算
        if (!isFinite(mx) || !isFinite(my)
            || !isFinite(px) || !isFinite(py)
            || !isFinite(scale) || scale <= 0) {
            return false;
        }

        var newX = mx - px * scale;
        var newY = my - py * scale;

        // ★ 计算结果也要有限
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

    // ========================================================
    // 渲染标签
    // ========================================================
    renderLabels: function() {
        var me = this;
        if (me._destroyed) return;
        var layer = me.labelLayer;
        if (!layer || !me.cachedOrigin.ready || !me.cs || !me.cs.dataset) return;

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

                el.addEventListener('dblclick', function(e) {
                    if (me._destroyed) return;
                    e.stopPropagation();
                    e.preventDefault();
                    var uuid = el.dataset.uuid;
                    var b = me.bindingMap.get(uuid);
                    if (b) {
                        try { me.openBindingItemHistory(b); }
                        catch (err) {
                            console.warn('[RealtimeSceneModelPanel] 标签双击打开历史失败', err);
                        }
                    }
                });

                layer.appendChild(el);
            }
            
            // 计算背景色和阴影
            var bgColor = me.buildFillColor(style.bgColor, style.bgOpacity);
            var boxShadow = (style.bgOpacity == 0) ? 'none' : '0 2px 6px rgba(0,0,0,0.18)';
            
            var labelText = me.buildLabelText(binding, style);
            el.innerHTML = labelText;
            
            var isHidden = style.hide || (labelText === '');

            // ★ 核心修改：处理设备通信状态改变字色
//            var textColor = style.textColor;
//            var commStatus = binding.commStatus != null ? parseInt(binding.commStatus, 10) : 0;
//            
//            if (commStatus === 2) {
//                // 状态 2：上线中
//                if (me.alarmShowStyle && me.alarmShowStyle.Comm && me.alarmShowStyle.Comm.goOnline && me.alarmShowStyle.Comm.goOnline.Color) {
//                    var c = (me.alarmShowStyle.Comm.goOnline.Color + '').trim();
//                    if (/^#?[0-9a-fA-F]{6}$/.test(c)) {
//                        textColor = (c.charAt(0) === '#') ? c : ('#' + c);
//                    }
//                }
//            } else if (commStatus === 0 || (commStatus !== 1 && commStatus !== 2)) {
//                // 状态 0 或 非1非2：离线
//                if (me.alarmShowStyle && me.alarmShowStyle.Comm && me.alarmShowStyle.Comm.offline && me.alarmShowStyle.Comm.offline.Color) {
//                    var c = (me.alarmShowStyle.Comm.offline.Color + '').trim();
//                    if (/^#?[0-9a-fA-F]{6}$/.test(c)) {
//                        textColor = (c.charAt(0) === '#') ? c : ('#' + c);
//                    }
//                }
//            }
//
//            el.style.cssText = 'position:absolute;padding:4px 10px;border-radius:4px;white-space:nowrap;' +
//                'font-weight:600;transform:translate(-50%,' + translateY + ');box-shadow:' + boxShadow + ';' +
//                'pointer-events:auto;cursor:pointer;' +
//                'left:' + cx + 'px;top:' + cy + 'px;' +
//                'background:' + bgColor + ';' +
//                'color:' + textColor + ';font-size:' + style.font + ';' + // ★ 使用动态 textColor
//                'display:' + (isHidden ? 'none' : 'block') + ';';
            
            
            // 使用配置的原始文字颜色，不关联通信状态
            var textColor = style.textColor;

            el.style.cssText = 'position:absolute;padding:4px 10px;border-radius:4px;white-space:nowrap;' +
                'font-weight:600;transform:translate(-50%,' + translateY + ');box-shadow:' + boxShadow + ';' +
                'pointer-events:auto;cursor:pointer;' +
                'left:' + cx + 'px;top:' + cy + 'px;' +
                'background:' + bgColor + ';' +
                'color:' + textColor + ';font-size:' + style.font + ';' +
                'display:' + (isHidden ? 'none' : 'block') + ';';

            visible[shape.uuid] = true;
        });

        Object.keys(existing).forEach(function(uuid) {
            if (!visible[uuid] && existing[uuid].parentNode) {
                existing[uuid].parentNode.removeChild(existing[uuid]);
            }
        });
    },

    // ========================================================
    // ★ 双击标注（图形 / 标签）：打开绑定数据项的历史数据 / 历史曲线
    // ========================================================
    openBindingItemHistory: function(binding) {
        var me = this;
        if (me._destroyed) return;
        if (!binding) return;

        var itemName  = binding.fieldLabel || binding.fieldName;
        var itemValue = binding.currentValue;

        var cellInfo = {
            column:         binding.fieldName,
            type:           (binding.itemType != null && binding.itemType !== '')
                                ? parseInt(binding.itemType, 10) : NaN,
            resolutionMode: (binding.resolutionMode != null && binding.resolutionMode !== '')
                                ? parseInt(binding.resolutionMode, 10) : NaN,
            bitIndex:       binding.bitIndex,
            columnDataType: binding.columnDataType || '',
            deviceId:       binding.deviceId,
            deviceName:     binding.deviceName,
            calculateType:  me.calculateType,
            fromSceneModelAnnotation: true
        };

        // ---- 兜底：无法识别类型时按值的类型决定 ----
        if (isNaN(cellInfo.type)) {
            if (isNumber(itemValue)) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
            else                     viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            return;
        }

        // ---- 与 viewDeviceRealTimeMonitoringData 里的分支一致 ----
        if (cellInfo.type == 0) {
            if (cellInfo.resolutionMode == 2) {
                if (isNotVal(cellInfo.columnDataType)
                    && cellInfo.columnDataType.toUpperCase() != 'string'.toUpperCase()
                    && isNumber(itemValue)) {
                    viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                } else {
                    viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                }
            } else if (cellInfo.resolutionMode == 0) {
                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            } else if (cellInfo.resolutionMode == 1) {
                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            } else {
                if (isNumber(itemValue)) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                else                     viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            }
        } else if (cellInfo.type == 1) {
            var isNumItem = false;
            try {
                isNumItem = (typeof isNumByCalculateItemCode === 'function')
                          && isNumByCalculateItemCode(cellInfo.column);
            } catch (e) { isNumItem = false; }
            if (isNumItem) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
            else           viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
        } else if (cellInfo.type == 3) {
            if (isNumber(itemValue)) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
            else                     viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
        } else if (cellInfo.type == 5) {
            if (cellInfo.resolutionMode == 2) {
                if (isNumber(itemValue)) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                else                     viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            } else if (cellInfo.resolutionMode == 0) {
                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            } else if (cellInfo.resolutionMode == 1) {
                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            } else if (cellInfo.resolutionMode == 7) {
                if (isNumber(itemValue)) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                else                     viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            } else {
                if (isNumber(itemValue)) viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                else                     viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
            }
        } else {
            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
        }
    },

    // ========================================================
    // 构建标签文本
    // ========================================================
    buildLabelText: function(b, labelStyle) {
        if (!b || !b.deviceId || !b.fieldName) return '未绑定';

        var me = this;
        var fldName = b.fieldLabel || b.fieldName;
        var devName = b.deviceName || b.deviceId;
        
        var hideDev = !labelStyle || labelStyle.hideDeviceName !== false;
        var hideFld = labelStyle && labelStyle.hideFieldName === true;

        // 根据勾选状态计算前缀
        var prefix = '';
        if (!hideDev && !hideFld) {
            prefix = devName + '.' + fldName;
        } else if (!hideDev && hideFld) {
            prefix = devName;
        } else if (hideDev && !hideFld) {
            prefix = fldName;
        } else {
            // 都隐藏，prefix 为空
            prefix = '';
        }

        var val = b.currentValue;
        
        // 如果都隐藏且没值，直接返回空（让 renderLabels 去隐藏 DOM）
        if (prefix === '' && (val == null || val === '' || val === '--')) {
            return '';
        }

        // 如果没有值，只显示前缀（如果前缀也为空，上面已经拦截了）
        if (val == null || val === '' || val === '--') {
            return Ext.String.htmlEncode(prefix);
        }

        var unit = b.unit;
        var unitStr = (unit != null && unit !== '') ? ('(' + unit + ')') : '';
        var prefixHtml = prefix ? (Ext.String.htmlEncode(prefix) + '：') : '';
        
        var valHtml = Ext.String.htmlEncode(val + unitStr);

        var alarmLevel = parseInt(b.alarmLevel, 10) || 0;
        var valColor = null;

        if (alarmLevel > 0 && me.alarmShowStyle && me.alarmShowStyle.Data) {
            var cfg = null;
            if (alarmLevel === 100)      cfg = me.alarmShowStyle.Data.FirstLevel;
            else if (alarmLevel === 200) cfg = me.alarmShowStyle.Data.SecondLevel;
            else if (alarmLevel === 300) cfg = me.alarmShowStyle.Data.ThirdLevel;

            if (cfg && cfg.Color) {
                var c = (cfg.Color + '').trim();
                // ★ 只接受 #RRGGBB 或 RRGGBB 两种合法格式，其他一律忽略
                if (/^#?[0-9a-fA-F]{6}$/.test(c)) {
                    valColor = (c.charAt(0) === '#') ? c : ('#' + c);
                }
            }
        }

        if (valColor) {
            return prefixHtml + '<span style="color:' + valColor + ';font-weight:bold;">' + valHtml + '</span>';
        }
        return prefixHtml + valHtml;
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
        if (me._destroyed) return;
        Ext.defer(function() {
            if (me._destroyed) return;
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 30);
    },

    // ========================================================
    // WebSocket 推送：同步值 + 报警等级
    // ========================================================
    updateRealtimeValuesFromPush: function(pushDeviceId, itemList) {
        var me = this;
        if (me._destroyed) return;
        if (pushDeviceId == null) return;
        if (!Ext.isArray(itemList) || itemList.length === 0) return;
        if (!me.deviceIndexMap || me.deviceIndexMap.size === 0) return;

        // ★ O(1) 判断：索引里有没有这个设备ID
        var uuidSet = me.deviceIndexMap.get(String(pushDeviceId));
        if (!uuidSet || uuidSet.size === 0) return;

        // 构建 column → item 映射，方便按 fieldName 查值
        var valueMap = {};
        itemList.forEach(function(item) {
            if (!item || !item.column) return;
            valueMap[String(item.column).toUpperCase()] = item;
        });

        var changed = false;

        // ★ 只遍历该设备绑定的 uuid（通常 1~5 个）
        uuidSet.forEach(function(uuid) {
            var binding = me.bindingMap.get(uuid);
            if (!binding || !binding.fieldName) return;

            // 收到实时数据，说明设备在线，强制更新通信状态为 1
            if (binding.commStatus !== 1) {
                binding.commStatus = 1;
                changed = true;
            }

            var item = valueMap[String(binding.fieldName).toUpperCase()];
            if (!item) return;

            // ---- 值 ----
            var newValue = (item.value != null && item.value !== '')
                         ? item.value
                         : item.rawValue;

            if (String(binding.currentValue) !== String(newValue)) {
                binding.currentValue = newValue;
                changed = true;
            }

            // ---- 报警等级 ----
            if (item.alarmLevel !== undefined && item.alarmLevel !== null) {
                var newLevelStr = String(item.alarmLevel);
                var oldLevelStr = (binding.alarmLevel !== undefined && binding.alarmLevel !== null)
                                ? String(binding.alarmLevel)
                                : '0';

                if (oldLevelStr !== newLevelStr) {
                    binding.alarmLevel = newLevelStr;
                    changed = true;
                }
            }
        });

        if (changed) me.scheduleRenderLabels();
    },

    updateRealtimeValue: function(uuid, value, unit) {
        var me = this;
        if (me._destroyed) return;
        if (!uuid) return;
        var binding = me.bindingMap.get(uuid);
        if (!binding) return;
        binding.currentValue = value;
        if (unit != null) binding.unit = unit;
        me.scheduleRenderLabels();
    },

    updateRealtimeValues: function(list) {
        var me = this;
        if (me._destroyed) return;
        if (!Ext.isArray(list)) return;
        var changed = false;
        list.forEach(function(item) {
            var binding = me.bindingMap.get(item.uuid);
            if (!binding) return;
            if (item.value !== undefined) {
                binding.currentValue = item.value;
                changed = true;
            }
            if (item.unit != null) binding.unit = item.unit;
            if (item.alarmLevel !== undefined && item.alarmLevel !== null) {
                binding.alarmLevel = item.alarmLevel;
                changed = true;
            }
        });
        if (changed) me.scheduleRenderLabels();
    },
    
    // ========================================================
    // WebSocket 推送：同步设备通信状态
    // ========================================================
    updateRealtimeCommStatus: function(pushDeviceId, commStatus) {
        var me = this;
        if (me._destroyed) return;
        if (pushDeviceId == null || commStatus == null) return;
        if (!me.deviceIndexMap || me.deviceIndexMap.size === 0) return;

        // 判断该设备是否在当前场景中
        var uuidSet = me.deviceIndexMap.get(String(pushDeviceId));
        if (!uuidSet || uuidSet.size === 0) return;

        var changed = false;
        var newStatus = parseInt(commStatus, 10);
        
        // 遍历该设备绑定的所有标注，更新 commStatus
        uuidSet.forEach(function(uuid) {
            var binding = me.bindingMap.get(uuid);
            if (binding) {
                var oldStatus = binding.commStatus != null ? parseInt(binding.commStatus, 10) : 0;
                if (oldStatus !== newStatus) {
                    binding.commStatus = newStatus;
                    changed = true;
                }
            }
        });

        if (changed) me.scheduleRenderLabels();
    },

    // ========================================================
    // 销毁
    //   ★ 显式清空所有绑定相关 Map + 清空 labelLayer + 停止 rAF
    // ========================================================
    beforeDestroy: function() {
        // ★ 置销毁标志，阻止异步回调再操作已销毁的组件
        this._destroyed = true;

        if (this._originTimer) { clearInterval(this._originTimer); this._originTimer = null; }
        if (this._resizeTimer) { clearTimeout(this._resizeTimer); this._resizeTimer = null; }

        if (this._visibilityHandler) {
            try { document.removeEventListener('visibilitychange', this._visibilityHandler); } catch (e) {}
            this._visibilityHandler = null;
        }

        // ★ 清空 rAF 标志，防止回调再触发
        this._rafPending = false;

        // ★ 清空 labelLayer 中的标签 DOM，避免内存泄漏
        if (this.labelLayer) {
            try { this.labelLayer.innerHTML = ''; } catch (e) {}
            this.labelLayer = null;
        }

        // ★ 显式清空所有 Map
        this._clearAllBindingMaps();
        this.bindingMap       = null;
        this.deviceIndexMap   = null;
        this.labelStyleMap    = null;
        this.userColorMap     = null;
        this.userLineWidthMap = null;

        // ★ 销毁 canvas-select
        try { if (this.cs && typeof this.cs.destroy === 'function') this.cs.destroy(); } catch (e) {}
        this.cs = null;

        this._resizing = false;
        this._settingImage = false;
        this._rebuilding = false;

        this.callParent(arguments);
    }
});