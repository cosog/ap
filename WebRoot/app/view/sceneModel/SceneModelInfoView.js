var loginUserSceneModelRight = getRoleModuleRight('SceneModel');

Ext.define("AP.view.sceneModel.SceneModelInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sceneModelInfoView',
    layout: 'border',
    border: false,

    // ========================================================
    // 内部状态（属性直接挂在实例上）
    // ========================================================
    cs: null,
    labelLayer: null,
    currentRecord: null,
    cachedOrigin: { x: 0, y: 0, scale: 1, ready: false },
    bindingMap: null,
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

    // ========================================================
    // 一次性内联所有布局
    // ========================================================
    initComponent: function() {
        var me = this;
        me.bindingMap = new Map();
        me.labelStyleMap = new Map();

        Ext.apply(me, {
            tbar: [],
            items: [
                // ============ 左侧：场景列表 ============
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
                        xtype: 'button',
                        text: loginUserLanguageResource.add,
                        iconCls: 'add',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onCreateScene(); }
                    }, "-", {
                        xtype: 'button',
                        text: loginUserLanguageResource.deleteData,
                        iconCls: 'delete',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onDeleteScene(); }
                    }, "-", {
                        xtype: 'button',
                        text: loginUserLanguageResource.save,
                        iconCls: 'save',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onSaveScene(); }
                    }, "-", {
                        xtype: 'button',
                        text: loginUserLanguageResource.exportData,
                        iconCls: 'export',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onExportScenes(); }
                    }, "-", {
                        xtype: 'button',
                        text: loginUserLanguageResource.importData,
                        iconCls: 'import',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onImportScenes(); }
                    }]
                },

                // ============ 右侧：场景配置（图形标注） ============
                {
                    region: 'center',
                    id: 'SceneModelConfigPanel_Id',
                    layout: 'border',
                    border: false,
                    // ★ 工具条直接配置在 tbar 里
                    tbar: [
                        { xtype: 'button', text: '✋ 选择', itemId: 'btnSelect',
                          enableToggle: true, pressed: true, toggleGroup: 'createType',
                          handler: function(b) { if (b.pressed && me.cs) me.cs.createType = 0; }
                        },
                        { xtype: 'button', text: '▭ 矩形', itemId: 'btnRect',
                          enableToggle: true, toggleGroup: 'createType',
                          handler: function(b) { if (b.pressed && me.cs) me.cs.createType = 1; }
                        },
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
                        { xtype: 'button', text: '💾 保存标注', cls: 'x-btn-primary',
                          handler: function() { me.onSaveAnnotations(); } }
                    ],
                    // ★ items 里只剩画布和样式面板，不再需要 region: north
                    items: [
                        {
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
                        },
                        {
                            xtype: 'panel',
                            region: 'east',
                            width: 280,
                            title: '样式设置',
                            collapsible: true,
                            split: true,
                            bodyPadding: 12,
                            autoScroll: true,
                            items: [{
                                xtype: 'fieldset',
                                title: '图形样式',
                                defaults: { anchor: '100%', margin: '6 0' },
                                items: [
                                    { xtype: 'textfield', fieldLabel: '边框颜色', itemId: 'strokeStyle',
                                      value: '#00FF00',
                                      listeners: { change: function() { me.onGraphicStyleChange(); } } },
                                    { xtype: 'numberfield', fieldLabel: '边框宽度', itemId: 'lineWidth',
                                      value: 1, minValue: 0, maxValue: 10, allowDecimals: false,
                                      listeners: { change: function() { me.onGraphicStyleChange(); } } },
                                    { xtype: 'textfield', fieldLabel: '填充颜色', itemId: 'fillStyle',
                                      value: '#0000FF',
                                      listeners: { change: function() { me.onGraphicStyleChange(); } } },
                                    { xtype: 'numberfield', fieldLabel: '填充透明度%', itemId: 'fillOpacity',
                                      value: 25, minValue: 0, maxValue: 100, allowDecimals: false,
                                      listeners: { change: function() { me.onGraphicStyleChange(); } } },
                                    { xtype: 'numberfield', fieldLabel: '控制点大小', itemId: 'ctrlRadius',
                                      value: 3, minValue: 2, maxValue: 10, allowDecimals: false,
                                      listeners: { change: function() { me.onGraphicStyleChange(); } } }
                                ]
                            }, {
                                xtype: 'fieldset',
                                title: '标签样式',
                                defaults: { anchor: '100%', margin: '6 0' },
                                items: [
                                    { xtype: 'combo', fieldLabel: '字体', itemId: 'labelFont',
                                      store: ['11px', '12px', '13px', '14px'],
                                      value: '12px', editable: false,
                                      listeners: { change: function() { me.onLabelStyleChange(); } } },
                                    { xtype: 'textfield', fieldLabel: '标签背景', itemId: 'labelBg',
                                      value: '#4A6CF7',
                                      listeners: { change: function() { me.onLabelStyleChange(); } } },
                                    { xtype: 'numberfield', fieldLabel: '背景透明度%', itemId: 'labelBgOpacity',
                                      value: 100, minValue: 0, maxValue: 100, allowDecimals: false,
                                      listeners: { change: function() { me.onLabelStyleChange(); } } },
                                    { xtype: 'textfield', fieldLabel: '文字颜色', itemId: 'labelText',
                                      value: '#FFFFFF',
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

        // ★ 给中心面板挂上 loadScene/resetPanel，供 Store 里的 Ext.getCmp('SceneModelConfigPanel_Id') 调用
        var centerPanel = Ext.getCmp('SceneModelConfigPanel_Id');
        centerPanel.loadScene = function(r) { me.loadScene(r); };
        centerPanel.resetPanel = function() { me.resetPanel(); };
        centerPanel.getSceneConfig = function() { return me.getConfig(); };

        // DOM 就绪后初始化 canvas-select
        me.on('afterrender', function() {
            Ext.defer(me.initCanvasSelect, 100, me);
        });
    },

    // ========================================================
    // 初始化 canvas-select
    // ========================================================
    initCanvasSelect: function() {
        var me = this;
        var canvasCmp = me.down('#canvas');
        var layerCmp = me.down('#labelLayer');
        if (!canvasCmp || !layerCmp) return;

        var canvasEl = canvasCmp.el.dom;
        me.labelLayer = layerCmp.el.dom;

        if (typeof CanvasSelect === 'undefined') {
            Ext.Msg.alert('错误', 'CanvasSelect 库未加载');
            return;
        }

        me.cs = new CanvasSelect(canvasEl, '');
        me.cs.ctrlRadius = me.globalStyle.ctrlRadius;
        me.cs.createType = 0;
        me.cs.scrollZoom = true;
        me.cs.hideLabel = true;

        me.cs.on('load', function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        });
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

    // ========================================================
    // 画布事件
    // ========================================================
    onShapeAdded: function(info) {
        var me = this;
        var uuid = me.extractUuid(info);
        if (!uuid) return;
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

        var fillParts = me.parseFillStyle(shape.fillStyle || '#0000FF40');
        var panel = me.down('panel[region=east]');
        panel.down('#strokeStyle').setValue(shape._userStrokeStyle || shape.strokeStyle || '#00FF00');
        panel.down('#lineWidth').setValue(shape.lineWidth != null ? shape.lineWidth : 1);
        panel.down('#fillStyle').setValue(fillParts.hex);
        panel.down('#fillOpacity').setValue(fillParts.alpha);
        panel.down('#ctrlRadius').setValue(shape.ctrlRadius || 3);
    },

    afterZoom: function() {
        var me = this;
        Ext.defer(function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 30);
    },

    // ========================================================
    // 图片操作
    // ========================================================
    onSelectServerImage: function() {
        var me = this;

        // 图片列表 store
        var imageStore = Ext.create('Ext.data.Store', {
            fields: ['name', 'url', 'size', 'lastModified'],
            proxy: {
                type: 'ajax',
                url: context + '/sceneModelController/getSceneImageList',
                reader: { type: 'json', rootProperty: 'data' }
            },
            autoLoad: true
        });

        var win = Ext.create('Ext.window.Window', {
            title: '选择场景图片',
            width: 820, height: 580,
            modal: true,
            layout: 'fit',
            closeAction: 'destroy',
            items: [{
                xtype: 'panel',
                layout: 'fit',
                tbar: [
                    { xtype: 'textfield', itemId: 'searchField',
                      emptyText: '搜索文件名...', width: 220,
                      listeners: { change: function(f, v) { win.filterImages(v); } }
                    },
                    '->',
                    { xtype: 'button', text: '📤 上传本地图片',
                      handler: function() { win.doUpload(); }
                    },
                    { xtype: 'button', text: '🔄 刷新',
                      handler: function() {
                          imageStore.reload({
                              callback: function() {
                                  win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                              }
                          });
                      }
                    }
                ],
                items: [{
                    xtype: 'dataview',
                    itemId: 'imageView',
                    itemSelector: 'div.scene-image-item',
                    overItemCls: 'scene-image-item-over',
                    selectedItemCls: 'scene-image-item-selected',
                    emptyText: '<div style="padding:40px;color:#aaa;text-align:center;">'
                              + '暂无图片，请点击右上角"上传本地图片"</div>',
                    store: imageStore,
                    tpl: [
                        '<tpl for=".">',
                            '<div class="scene-image-item" style="display:inline-block;width:140px;height:170px;',
                            'margin:10px;text-align:center;cursor:pointer;border:2px solid #e9ecef;',
                            'border-radius:6px;padding:8px;background:#fff;vertical-align:top;">',
                                '<div style="height:110px;display:flex;align-items:center;justify-content:center;overflow:hidden;">',
                                    '<img src="{url}" style="max-width:120px;max-height:110px;object-fit:contain;"',
                                    ' onerror="this.style.display=\'none\'"/>',
                                '</div>',
                                '<div style="font-size:11px;color:#666;margin-top:6px;',
                                'overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="{name}">{name}</div>',
                            '</div>',
                        '</tpl>'
                    ],
                    listeners: {
                        itemdblclick: function(view, record) {
                            win.doSelect(record.get('url'));
                        }
                    }
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

            // ---- 方法 ----
            // 本地过滤
            filterImages: function(keyword) {
                var view = this.down('[itemId=imageView]');
                var allData = this.allImages || [];
                view.getStore().removeAll();
                if (!keyword) {
                    view.getStore().add(allData);
                    return;
                }
                var filtered = allData.filter(function(item) {
                    return item.name.toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
                });
                view.getStore().add(filtered);
            },

            // 选中图片
            doSelect: function(url) {
                me.cs.setImage(url);
                if (me.currentRecord) {
                    var cfg = me.currentRecord.getConfig();
                    cfg.imageUrl = url;
                    cfg.imageSource = 'server';
                    me.currentRecord.setConfig(cfg);
                }
                win.close();
            },

            // 弹窗内上传
            doUpload: function() {
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
                        headers: { 'Content-Type': null },
                        success: function(resp) {
                            Ext.MessageBox.hide();
                            var result = Ext.decode(resp.responseText);
                            if (result.success) {
                                Ext.toast('上传成功');
                                imageStore.reload({
                                    callback: function() {
                                        win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                                    }
                                });
                            } else {
                                Ext.Msg.alert('失败', result.message || '上传失败');
                            }
                        },
                        failure: function() {
                            Ext.MessageBox.hide();
                            Ext.Msg.alert('错误', '上传失败');
                        }
                    });
                };
                input.click();
            }
        });

        // 首次加载完成后缓存完整列表，用于本地过滤
        imageStore.on('load', function(store) {
            win.allImages = store.getRange().map(function(r) { return r.getData(); });
        });

        win.show();
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
                headers: { 'Content-Type': null },
                success: function(resp) {
                    Ext.MessageBox.hide();
                    try {
                        var result = Ext.decode(resp.responseText);
                        if (result.success) {
                            me.cs.setImage(result.imageUrl);
                            if (me.currentRecord) {
                                var cfg = me.currentRecord.getConfig();
                                cfg.imageUrl = result.imageUrl;
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
            Ext.Msg.alert('提示', '请先选择场景');
            return;
        }
        if (!me.cs) {
            Ext.Msg.alert('提示', '画布未初始化');
            return;
        }

        // 检查图片是否已选择
        var imageUrl = me.cs.image ? (me.cs.image.src || '') : '';
        if (!imageUrl) {
            Ext.Msg.alert('提示', '请先选择场景图片');
            return;
        }

        // 检查图片是否是本地临时 URL（blob/data），未上传
        if (imageUrl.indexOf('blob:') === 0 || imageUrl.indexOf('data:') === 0) {
            Ext.Msg.confirm('提示', '当前图片还未上传到服务器，是否先上传？', function(btn) {
                if (btn === 'yes') {
                    me.onUploadLocalImage();   // 上传后再点保存
                }
            });
            return;
        }

        // 组装 config
        var cfg = me.getConfig();
        if (!cfg.shapes || cfg.shapes.length === 0) {
            Ext.Msg.confirm('提示', '当前场景还没有任何标注，仍要保存吗？', function(btn) {
                if (btn === 'yes') me.submitSaveScene(cfg);
            });
            return;
        }

        me.submitSaveScene(cfg);
    },

    // ============================================================
    // 提交保存到后端
    // ============================================================
    submitSaveScene: function(cfg) {
        var me = this;
        var record = me.currentRecord;

        // 先写回本地 record，防止切换场景时数据丢失
        record.setConfig(cfg);
        Ext.MessageBox.show({
            msg: '正在保存场景标注...',
            progressText: '保存中',
            width: 300,
            wait: true,
            waitConfig: { interval: 200 }
        });

        Ext.Ajax.request({
            url: context + '/sceneModelController/saveSceneModel',
            method: 'POST',
            params: {
                id: record.get('modelId'),
                config: JSON.stringify(cfg)
            },
            success: function(resp) {
                Ext.MessageBox.hide();
                try {
                    var result = Ext.decode(resp.responseText);
                    if (result.success) {
                        // ★ 提交本地修改，标记为 clean，避免下次切换场景时出现"未保存"提示
                        record.commit();

                        // ★ 刷新左侧列表里当前行的 config 列（如果 grid 显示 config 字段的话）
                        // record.set('config', JSON.stringify(cfg));

                        Ext.toast({
                            html: '保存成功',
                            title: '成功',
                            width: 200,
                            align: 't'
                        });
                    } else {
                        Ext.Msg.alert('保存失败', result.message || '未知错误');
                    }
                } catch (e) {
                    Ext.Msg.alert('错误', '服务器返回数据异常');
                }
            },
            failure: function(response) {
                Ext.MessageBox.hide();
                Ext.Msg.alert('错误', '网络请求失败：' + (response.status || ''));
            }
        });
    },
    
    
    

    // ========================================================
    // 数据源绑定弹窗
    // ========================================================
    openDataSourceWindow: function(uuid, isEdit) {
        var me = this;
        if (!uuid) return;

        var existing = me.bindingMap.get(uuid) || {};

        // 从当前场景记录取组织 id
        var orgId = '';
        if (me.currentRecord) {
            orgId = me.currentRecord.get('orgid') || me.currentRecord.get('orgId') || '';
        }

        // 先销毁可能存在的旧窗口，避免 id 冲突
        var oldWin = Ext.getCmp('sceneDataSourceWindow');
        if (oldWin) oldWin.close();

        var oldDeviceGrid = Ext.getCmp('SceneDeviceGrid_Id');
        if (oldDeviceGrid) oldDeviceGrid.destroy();
        var oldFieldGrid = Ext.getCmp('SceneDeviceFieldGrid_Id');
        if (oldFieldGrid) oldFieldGrid.destroy();

        Ext.create('AP.view.sceneModel.DataSourceWindow', {
            targetUuid: uuid,
            targetOrgId: orgId,
            currentBinding: {
                deviceId:   existing.deviceId   || '',
                fieldName:  existing.fieldName  || '',   // 逻辑键，等于 itemCode
                unit:       existing.unit       || ''
            },
            onConfirm: function(binding, uuid) {
                var old = me.bindingMap.get(uuid) || {};
                me.bindingMap.set(uuid, {
                    deviceId:     binding.deviceId,
                    deviceName:   binding.deviceName,
                    fieldName:    binding.fieldName,      // = itemCode
                    fieldLabel:   binding.fieldLabel,     // = itemName
                    unit:         binding.unit || '',
                    dataSource:   binding.dataSource || '',
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
    // 样式变更
    // ========================================================
    onGraphicStyleChange: function() {
        var me = this;
        var panel = me.down('panel[region=east]');
        var strokeStyle = panel.down('#strokeStyle').getValue();
        var lineWidth = panel.down('#lineWidth').getValue();
        var fillColor = panel.down('#fillStyle').getValue();
        var fillOpacity = panel.down('#fillOpacity').getValue();
        var ctrlRadius = panel.down('#ctrlRadius').getValue();

        if (!strokeStyle || !fillColor) return;

        var fillStyle = fillColor + me.toHexAlpha(fillOpacity);
        var effectiveStroke = lineWidth === 0 ? me.TRANSPARENT_STROKE : strokeStyle;

        if (me.lastSelectedUuid && me.cs) {
            var idx = -1;
            for (var i = 0; i < me.cs.dataset.length; i++) {
                if (me.cs.dataset[i].uuid === me.lastSelectedUuid) { idx = i; break; }
            }
            if (idx >= 0) {
                var shape = me.cs.dataset[idx];
                shape._userStrokeStyle = strokeStyle;
                shape.strokeStyle = effectiveStroke;
                shape.lineWidth = lineWidth;
                shape.fillStyle = fillStyle;
                shape.ctrlRadius = ctrlRadius;
                var snapshot = me.cs.dataset.map(function(s) { return Ext.apply({}, s); });
                me.cs.setData(snapshot);
            }
        } else if (me.cs) {
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
        var panel = me.down('panel[region=east]');
        var style = {
            font: panel.down('#labelFont').getValue(),
            bgColor: panel.down('#labelBg').getValue(),
            bgOpacity: panel.down('#labelBgOpacity').getValue(),
            textColor: panel.down('#labelText').getValue(),
            hide: panel.down('#hideLabel').getValue(),
            up: true
        };
        if (!style.bgColor || !style.textColor) return;

        if (me.lastSelectedUuid) {
            me.labelStyleMap.set(me.lastSelectedUuid, style);
        } else {
            Ext.apply(me.defaultLabelStyle, style);
        }
        me.scheduleRenderLabels();
    },

    // ========================================================
    // 标签覆盖层
    // ========================================================
    updateOriginIfNeeded: function(force) {
        if (!this.cs) return false;
        var cs = this.cs;
        var scale = cs.scale || 1;
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
        var devName = b.deviceName || b.deviceId;    // 兜底：用设备 ID
        var fldName = b.fieldLabel || b.fieldName;   // 兜底：用字段编码
        return devName + '：' + fldName;
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
            var xs = coor.map(function(p) { return p[0]; });
            var ys = coor.map(function(p) { return p[1]; });
            return {
                x: (Math.min.apply(null, xs) + Math.max.apply(null, xs)) / 2,
                y: Math.min.apply(null, ys)
            };
        }
        return null;
    },

    // ========================================================
    // 加载 / 导出 config
    // ========================================================
 // ========================================================
 // 点击场景时加载（从数据库读取 config）
 // ========================================================
 loadScene: function(record) {
     var me = this;

     if (!me.cs) {
         // canvas 还没初始化，延后重试
         Ext.defer(function() { me.loadScene(record); }, 100, me);
         return;
     }

     // 无记录 → 清空
     if (!record) {
         me.currentRecord = null;
         me.clearScene();
         return;
     }

     // 先清空当前内容
     me.currentRecord = record;
     me.clearScene();

     // 请求数据库拿 config
     Ext.MessageBox.show({
         msg: '正在加载场景配置...',
         progressText: '加载中',
         width: 300,
         wait: true,
         waitConfig: { interval: 200 }
     });

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
                     // config 可能是字符串，也可能是对象
                     if (typeof cfg === 'string') {
                         try { cfg = JSON.parse(cfg); } catch (e) { cfg = null; }
                     }
                     if (!cfg) cfg = { imageUrl: '', shapes: [] };

                     me.renderSceneConfig(cfg);
                 } else {
                     Ext.Msg.alert('加载失败', result.message || '未知错误');
                 }
             } catch (e) {
                 Ext.Msg.alert('错误', '服务器返回数据异常');
             }
         },
         failure: function() {
             Ext.MessageBox.hide();
             Ext.Msg.alert('错误', '网络请求失败');
         }
     });
 },

 // ========================================================
 // 清空画布和状态
 // ========================================================
 clearScene: function() {
     var me = this;
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
 // 用 config 渲染图片 + 标注 + 标签
 // ========================================================
 renderSceneConfig: function(cfg) {
     var me = this;
     if (!cfg) cfg = { imageUrl: '', shapes: [] };

     // 恢复默认样式
     if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
     if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

     // 加载图片
     if (cfg.imageUrl) {
         me.cs.setImage(cfg.imageUrl);
     }

     // 恢复 binding 和 labelStyle
     var shapes = cfg.shapes || [];
     shapes.forEach(function(s) {
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

     // 把纯 shape 数据传给 canvas-select
     var pureShapes = shapes.map(function(s) {
         return {
             uuid: s.uuid, type: s.type, coor: s.coor,
             strokeStyle: s.strokeStyle, fillStyle: s.fillStyle,
             lineWidth: s.lineWidth, ctrlRadius: s.ctrlRadius
         };
     });
     me.cs.setData(pureShapes);

     // 等图片和图形都加载完，刷新标签
     Ext.defer(function() {
         me.updateOriginIfNeeded(true);
         me.scheduleRenderLabels();
     }, 100);
 },

    getConfig: function() {
        var me = this;
        return {
            imageUrl: me.cs && me.cs.image ? (me.cs.image.src || '') : '',
            defaultLabelStyle: Ext.apply({}, me.defaultLabelStyle),
            globalStyle: Ext.apply({}, me.globalStyle),
            shapes: me.cs ? me.cs.dataset.map(function(s) {
                var binding = me.bindingMap.get(s.uuid) || {};
                var labelStyle = me.labelStyleMap.get(s.uuid) || me.defaultLabelStyle;
                return {
                    uuid: s.uuid, type: s.type, coor: s.coor,
                    strokeStyle: s._userStrokeStyle || s.strokeStyle,
                    fillStyle: s.fillStyle,
                    lineWidth: s.lineWidth,
                    ctrlRadius: s.ctrlRadius,
                    labelStyle: Ext.apply({}, labelStyle),

                    deviceId:     binding.deviceId,
                    deviceName:   binding.deviceName,
                    fieldName:    binding.fieldName,
                    fieldLabel:   binding.fieldLabel,
                    unit:         binding.unit,
                    dataSource:   binding.dataSource,
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
        me.lastSelectedUuid = null;
        if (me.cs) me.cs.setData([]);
        if (me.labelLayer) me.labelLayer.innerHTML = '';
    },

    // ========================================================
    // 场景增删改（工具栏按钮调用）
    // ========================================================
    onCreateScene: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;
        var store = grid.getStore();
        var rec = Ext.create('AP.model.sceneModel.SceneModel', {
            id: 0,
            orgid: Ext.getCmp('leftOrg_Id') ? Ext.getCmp('leftOrg_Id').getValue() : 0,
            devicetype: 101,
            name_zh_CN: '', name_en: '', name_ru: '',
            config: JSON.stringify({ imageUrl: '', shapes: [] }),
            sort: store.getCount() + 1
        });
        store.add(rec);
        grid.getSelectionModel().select(rec);
    },

    onDeleteScene: function() {
        var me = this;
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;
        var selected = grid.getSelectionModel().getSelection();
        if (selected.length === 0) { Ext.Msg.alert('提示', '请选择要删除的场景'); return; }
        Ext.Msg.confirm('确认', '确定要删除选中的场景吗？', function(btn) {
            if (btn !== 'yes') return;
            Ext.Ajax.request({
                url: context + '/sceneModelController/deleteSceneModel',
                method: 'POST',
                params: { ids: selected.map(function(r) { return r.get('id'); }).join(',') },
                success: function() {
                    grid.getStore().load();
                    me.resetPanel();
                }
            });
        });
    },

    onSaveScene: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;
        var store = grid.getStore();
        var payload = store.getRange().map(function(r) {
            return {
                id: r.get('id'), orgid: r.get('orgid'), devicetype: r.get('devicetype'),
                name_zh_CN: r.get('name_zh_CN'),
                name_en: r.get('name_en'),
                name_ru: r.get('name_ru'),
                sort: r.get('sort'),
                config: r.get('config') || JSON.stringify({ imageUrl: '', shapes: [] })
            };
        });
        Ext.Ajax.request({
            url: context + '/sceneModelController/saveSceneModelList',
            method: 'POST',
            jsonData: { list: payload },
            success: function() {
                Ext.Msg.alert('成功', '场景列表已保存');
                store.load();
            }
        });
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
        input.type = 'file';
        input.accept = '.json';
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
                } catch (err) {
                    Ext.Msg.alert('错误', '文件格式不正确');
                }
            };
            reader.readAsText(file);
        };
        input.click();
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

    beforeDestroy: function() {
        if (this.originTimer) clearInterval(this.originTimer);
        this.callParent(arguments);
    }
});