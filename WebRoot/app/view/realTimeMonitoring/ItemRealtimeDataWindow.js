var itemRealtimeDataHandsontableHelper = null;

Ext.define("AP.view.realTimeMonitoring.ItemRealtimeDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemRealtimeDataWindow',
    id: 'ItemRealtimeDataWindow_Id',
    layout: 'fit',
    title: loginUserLanguageResource.dynamicData,
    border: false,
    hidden: false,
    constrainHeader: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    width: 500,
    minWidth: 500,
    height: '80%',
    draggable: true,
    modal: true,

    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            tbar: [
                { id: 'RealtimeDataItemName_Id', xtype: 'textfield', value: '', hidden: true },
                { id: 'RealtimeDataItemCode_Id', xtype: 'textfield', value: '', hidden: true },
                { id: 'RealtimeDataItemType_Id', xtype: 'textfield', value: '', hidden: true },
                { id: 'RealtimeDataItemResolutionMode_Id', xtype: 'textfield', value: '', hidden: true },
                { id: 'RealtimeDataItemBitIndex_Id', xtype: 'textfield', value: '', hidden: true },
                '->',
                { xtype: 'button', text: loginUserLanguageResource.exportData, iconCls: 'export', handler: exportItemRealtimeDataTable },
                '-',
                { id: 'ItemRealtimeDataCount_Id', xtype: 'component', tpl: loginUserLanguageResource.totalCount + ':{count}', hidden: false }
            ],
            items: [{
                id: 'ItemRealtimeDataPanel_Id',
                layout: 'fit',
                autoScroll: false,
                html: '<div id="ItemRealtimeDataDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (thisPanel, adjWidth, adjHeight, options) {
                        if (itemRealtimeDataHandsontableHelper && itemRealtimeDataHandsontableHelper.hot) {
                            var newWidth = adjWidth;
                            var newHeight = adjHeight;
                            var header = thisPanel.getHeader();
                            if (header) {
                                newHeight = newHeight - header.lastBox.height - 2;
                            }
                            itemRealtimeDataHandsontableHelper.hot.updateSettings({
                                width: newWidth,
                                height: newHeight
                            });
                        } else {
                            CreateItemRealtimeDataTable();
                        }
                    }
                }
            }],
            listeners: {
                beforeclose: function (panel) {
                    if (itemRealtimeDataHandsontableHelper) {
                        if (itemRealtimeDataHandsontableHelper.hot) {
                            itemRealtimeDataHandsontableHelper.hot.destroy();
                        }
                        itemRealtimeDataHandsontableHelper = null;
                    }
                },
                // 最大化时，确保窗口处于展开状态，并刷新表格
                maximize: function (win) {
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    if (win._panel) win._panel.show();
                    if (win._toolbar) win._toolbar.show();
                    var minBtn = win._minimizeBtn;
                    var expandBtn = win._expandBtn;
                    if (minBtn) minBtn.style.display = 'inline-block';
                    if (expandBtn) expandBtn.style.display = 'none';
                    var maxBtn = win._maximizeBtn;
                    var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'none';
                    if (restoreBtn) restoreBtn.style.display = 'inline-block';
                    var panel = Ext.getCmp('ItemRealtimeDataPanel_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
                restore: function (win) {
                    if (win._minimized) {
                        win._minimized = false;
                        win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        var minBtn = win._minimizeBtn;
                        var expandBtn = win._expandBtn;
                        if (minBtn) minBtn.style.display = 'inline-block';
                        if (expandBtn) expandBtn.style.display = 'none';
                    }
                    var maxBtn = win._maximizeBtn;
                    var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'inline-block';
                    if (restoreBtn) restoreBtn.style.display = 'none';
                    if (win._savedHeight && !win.maximized) {
                        win.setHeight(win._savedHeight);
                        win._savedHeight = null;
                    }
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    var panel = Ext.getCmp('ItemRealtimeDataPanel_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
//                afterrender: function (panel) {
//                    var win = panel;
//                    win.originalTitle = win.title;
//                    win._savedHeight = null;
//                    win._minimized = false;
//                    win._defaultHeight = win.getHeight();
//
//                    win._panel = win.down('#ItemRealtimeDataPanel_Id');
//                    win._toolbar = win.down('toolbar');
//                    if (win._toolbar) win._toolbar.show();
//
//                    var header = win.getHeader();
//                    if (!header) return;
//
//                    var headerEl = header.el.dom;
//                    var btnContainer = document.createElement('div');
//                    btnContainer.style.cssText = 
//                        'position:absolute; right:2px; top:0; height:100%;' +
//                        'display:flex; align-items:center; gap:2px; z-index:10;' +
//                        'padding:0 4px;';
//                    headerEl.style.position = 'relative';
//                    headerEl.appendChild(btnContainer);
//
//                    /**
//                     * 创建标题栏按钮（支持自定义图标类）
//                     * @param {String} text         备选文本（当 iconCls 为空时显示）
//                     * @param {String} title        提示文字
//                     * @param {Function} clickHandler 点击回调
//                     * @param {Boolean} isClose     是否为关闭按钮（影响悬停背景色）
//                     * @param {String} iconCls      可选，自定义图标类名（例如 'cancel'）
//                     */
//                    function createButton(text, title, clickHandler, isClose, iconCls) {
//                        var el;
//                        if (iconCls) {
//                            // 使用图标类
//                            el = document.createElement('div');
//                            el.className = iconCls;
//                            el.style.cssText = 
//                                'width:20px; height:20px; cursor:pointer;' +
//                                'background-position:center center; background-repeat:no-repeat;' +
//                                'background-size:contain; border-radius:2px;' +
//                                'transition:background-color 0.15s;' +
//                                'margin:0 2px; touch-action:manipulation;';
//                        } else {
//                            // 使用文本
//                            el = document.createElement('button');
//                            el.textContent = text;
//                            var defaultColor = '#404040';
//                            el.style.cssText = 
//                                'background:transparent; border:none; font-size:16px; font-weight:300;' +
//                                'cursor:pointer; padding:0 8px;' +
//                                'display:flex; align-items:center; justify-content:center; border-radius:2px;' +
//                                'transition:background 0.15s, color 0.15s;' +
//                                'color:' + defaultColor + '; touch-action:manipulation;' +
//                                'font-family:sans-serif; line-height:1;' +
//                                'min-width:30px; min-height:30px;';
//                        }
//                        el.title = title;
//
//                        // pointerdown 事件：执行业务逻辑 + 视觉反馈
//                        el.addEventListener('pointerdown', function(e) {
//                            e.preventDefault();
//                            e.stopPropagation();
//                            // 视觉反馈
//                            if (isClose) {
//                                this.style.background = '#e81123';
//                                if (!iconCls) this.style.color = '#ffffff';
//                            } else {
//                                this.style.background = 'rgba(0,0,0,0.15)';
//                            }
//                            // 执行业务逻辑
//                            clickHandler(e);
//                            // 释放指针捕获（如果有）
//                            this.releasePointerCapture(e.pointerId);
//                        }, { passive: false });
//
//                        // 指针释放和离开时恢复样式
//                        el.addEventListener('pointerup', function(e) {
//                            this.style.background = 'transparent';
//                            if (!iconCls) this.style.color = isClose ? '#404040' : '#404040';
//                        }, { passive: false });
//
//                        el.addEventListener('pointerleave', function(e) {
//                            this.style.background = 'transparent';
//                            if (!iconCls) this.style.color = isClose ? '#404040' : '#404040';
//                        }, { passive: false });
//
//                        return el;
//                    }
//
//                    // ---- 创建各按钮，指定图标类（如果不需要图标，传 null 或不传）----
//                    // 1. 最小化 - 使用 'down' 类（您已有）
//                    var minBtn = createButton('─', '最小化', function () {
//                        win._savedHeight = win.getHeight();
//                        var headerH = win.getHeader() ? win.getHeader().getHeight() : 30;
//                        if (win._panel) win._panel.hide();
//                        if (win._toolbar) win._toolbar.hide();
//                        win.setHeight(headerH);
//                        win._minimized = true;
//                        win.setTitle(win.originalTitle + ' (已最小化)');
//                        minBtn.style.display = 'none';
//                        expandBtn.style.display = 'inline-block';
//                    }, false, 'down');  // 使用 'down' 图标
//
//                    btnContainer.appendChild(minBtn);
//                    win._minimizeBtn = minBtn;
//
//                    // 2. 展开 - 使用 'sync' 或 'up'（您可新增或选用已有类）
//                    var expandBtn = createButton('⤢', '展开', function () {
//                        if (win._savedHeight) {
//                            win.setHeight(win._savedHeight);
//                            win._savedHeight = null;
//                        } else {
//                            win.setHeight(win._defaultHeight || '80%');
//                        }
//                        win._minimized = false;
//                        win.setTitle(win.originalTitle);
//                        if (win._panel) win._panel.show();
//                        if (win._toolbar) win._toolbar.show();
//                        win.updateLayout();
//                        expandBtn.style.display = 'none';
//                        minBtn.style.display = 'inline-block';
//                        var panel = Ext.getCmp('ItemRealtimeDataPanel_Id');
//                        if (panel) {
//                            Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
//                        }
//                    }, false, 'sync');  // 使用 'sync' 图标
//                    expandBtn.style.display = 'none';
//                    btnContainer.appendChild(expandBtn);
//                    win._expandBtn = expandBtn;
//
//                    // 3. 最大化 - 使用 'forward' 或 'add'
//                    var maxBtn = createButton('☐', '最大化', function () {
//                        if (!win.maximized) {
//                            win.maximize();
//                        }
//                    }, false, 'forward');
//                    btnContainer.appendChild(maxBtn);
//                    win._maximizeBtn = maxBtn;
//
//                    // 4. 还原 - 使用 'backwards' 或 'check1'
//                    var restoreBtn = createButton('⧉', '还原', function () {
//                        if (win.maximized) {
//                            win.restore();
//                        }
//                    }, false, 'backwards');
//                    restoreBtn.style.display = 'none';
//                    btnContainer.appendChild(restoreBtn);
//                    win._restoreBtn = restoreBtn;
//
//                    // 5. 关闭 - 使用 'cancel'（您已有）
//                    var closeBtn = createButton('✕', '关闭', function () {
//                        win.close();
//                    }, true, 'cancel');
//                    btnContainer.appendChild(closeBtn);
//
//                    // 标题栏点击展开（忽略按钮区域）
//                    header.el.on('click', function (e) {
//                        if (btnContainer.contains(e.target)) {
//                            return;
//                        }
//                        if (win._minimized && !win.maximized) {
//                            e.stopEvent();
//                            if (win._savedHeight) {
//                                win.setHeight(win._savedHeight);
//                                win._savedHeight = null;
//                            } else {
//                                win.setHeight(win._defaultHeight || '80%');
//                            }
//                            win._minimized = false;
//                            win.setTitle(win.originalTitle);
//                            win.updateLayout();
//                            if (win._panel) win._panel.show();
//                            if (win._toolbar) win._toolbar.show();
//                            expandBtn.style.display = 'none';
//                            minBtn.style.display = 'inline-block';
//                            var panel = Ext.getCmp('ItemRealtimeDataPanel_Id');
//                            if (panel) {
//                                Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
//                            }
//                        }
//                    });
//
//                    win.on('destroy', function () {
//                        if (btnContainer.parentNode) {
//                            btnContainer.parentNode.removeChild(btnContainer);
//                        }
//                    });
//                }
                afterrender: function (panel) {
                    var win = panel;
                    win.originalTitle = win.title;
                    win._savedHeight = null;
                    win._minimized = false;
                    win._defaultHeight = win.getHeight();

                    win._panel = win.down('#ItemRealtimeDataPanel_Id');
                    win._toolbar = win.down('toolbar');
                    if (win._toolbar) win._toolbar.show();

                    var header = win.getHeader();
                    if (!header) return;

                    var headerEl = header.el.dom;
                    var btnContainer = document.createElement('div');
                    btnContainer.style.cssText = 
                        'position:absolute; right:2px; top:0; height:100%;' +
                        'display:flex; align-items:center; gap:2px; z-index:10;' +
                        'padding:0 4px;';
                    headerEl.style.position = 'relative';
                    headerEl.appendChild(btnContainer);

                    function createButton(text, title, clickHandler, isClose) {
                        var btn = document.createElement('button');
                        btn.textContent = text;
                        btn.title = title;
                        var defaultColor = '#404040';
                        btn.style.cssText = 
                            'background:transparent; border:none; font-size:16px; font-weight:300;' +
                            'cursor:pointer; padding:0 8px;' +
                            'display:flex; align-items:center; justify-content:center; border-radius:2px;' +
                            'transition:background 0.15s, color 0.15s;' +
                            'color:' + defaultColor + '; touch-action:manipulation;' +
                            'font-family:sans-serif; line-height:1;' +
                            'min-width:30px; min-height:30px;';

                        // 使用 pointerdown 事件（统一鼠标和触摸）
                        btn.addEventListener('pointerdown', function(e) {
                            e.preventDefault();
                            e.stopPropagation();
                            // 视觉反馈
                            if (isClose) {
                                this.style.background = '#e81123';
                                this.style.color = '#ffffff';
                            } else {
                                this.style.background = 'rgba(0,0,0,0.15)';
                            }
                            // 执行业务逻辑
                            clickHandler(e);
                            // 释放指针捕获（如果有）
                            this.releasePointerCapture(e.pointerId);
                        }, { passive: false });

                        // 指针释放时恢复样式
                        btn.addEventListener('pointerup', function(e) {
                            this.style.background = 'transparent';
                            this.style.color = isClose ? defaultColor : defaultColor;
                        }, { passive: false });

                        btn.addEventListener('pointerleave', function(e) {
                            this.style.background = 'transparent';
                            this.style.color = isClose ? defaultColor : defaultColor;
                        }, { passive: false });

                        return btn;
                    }

                    // ---- 创建各按钮 ----
                    var minBtn = createButton('─', '', function () {
                        win._savedHeight = win.getHeight();
                        var headerH = win.getHeader() ? win.getHeader().getHeight() : 30;
                        if (win._panel) win._panel.hide();
                        if (win._toolbar) win._toolbar.hide();
                        win.setHeight(headerH);
                        win._minimized = true;
                        minBtn.style.display = 'none';
                        expandBtn.style.display = 'inline-block';
                    });
                    btnContainer.appendChild(minBtn);
                    win._minimizeBtn = minBtn;

                    var expandBtn = createButton('⤢', '', function () {
                        if (win._savedHeight) {
                            win.setHeight(win._savedHeight);
                            win._savedHeight = null;
                        } else {
                            win.setHeight(win._defaultHeight || '80%');
                        }
                        win._minimized = false;
                        win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        win.updateLayout();
                        expandBtn.style.display = 'none';
                        minBtn.style.display = 'inline-block';
                        var panel = Ext.getCmp('ItemRealtimeDataPanel_Id');
                        if (panel) {
                            Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
                        }
                    });
                    expandBtn.style.display = 'none';
                    btnContainer.appendChild(expandBtn);
                    win._expandBtn = expandBtn;

                    var maxBtn = createButton('☐', '', function () {
                        if (!win.maximized) {
                            win.maximize();
                        }
                    });
                    btnContainer.appendChild(maxBtn);
                    win._maximizeBtn = maxBtn;

                    var restoreBtn = createButton('⧉', '', function () {
                        if (win.maximized) {
                            win.restore();
                        }
                    });
                    restoreBtn.style.display = 'none';
                    btnContainer.appendChild(restoreBtn);
                    win._restoreBtn = restoreBtn;

                    var closeBtn = createButton('✕', '', function () {
                        win.close();
                    }, true);
                    btnContainer.appendChild(closeBtn);

                    // 标题栏点击展开（忽略按钮区域）
                    header.el.on('click', function (e) {
                        if (btnContainer.contains(e.target)) {
                            return;
                        }
                        if (win._minimized && !win.maximized) {
                            e.stopEvent();
                            if (win._savedHeight) {
                                win.setHeight(win._savedHeight);
                                win._savedHeight = null;
                            } else {
                                win.setHeight(win._defaultHeight || '80%');
                            }
                            win._minimized = false;
                            win.setTitle(win.originalTitle);
                            win.updateLayout();
                            if (win._panel) win._panel.show();
                            if (win._toolbar) win._toolbar.show();
                            expandBtn.style.display = 'none';
                            minBtn.style.display = 'inline-block';
                            var panel = Ext.getCmp('ItemRealtimeDataPanel_Id');
                            if (panel) {
                                Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
                            }
                        }
                    });

                    win.on('destroy', function () {
                        if (btnContainer.parentNode) {
                            btnContainer.parentNode.removeChild(btnContainer);
                        }
                    });
                }
            }
        });
        me.callParent(arguments);
    }
});

// ---------- 以下为原有函数，保持不变 ----------
function exportItemRealtimeDataTable() {
    var selectRowId = "RealTimeMonitoringInfoDeviceListSelectRow_Id";
    var gridPanelId = "RealTimeMonitoringListGridPanel_Id";
    var deviceName = '';
    var deviceId = 0;
    var calculateType = 0;
    var itemName = Ext.getCmp("RealtimeDataItemName_Id").getValue();
    var itemCode = Ext.getCmp("RealtimeDataItemCode_Id").getValue();
    var itemType = Ext.getCmp("RealtimeDataItemType_Id").getValue();
    var itemResolutionMode = Ext.getCmp("RealtimeDataItemResolutionMode_Id").getValue();
    var itemBitIndex = Ext.getCmp("RealtimeDataItemBitIndex_Id").getValue();
    var selectRow = Ext.getCmp(selectRowId).getValue();
    if (Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length > 0) {
        calculateType = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
        deviceId = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
        deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
    }

    var timestamp = new Date().getTime();
    var key = 'exportItemRealTimeData_' + deviceId + '_' + itemCode + '_' + timestamp;
    var maskPanelId = 'ItemRealtimeDataPanel_Id';
    var url = context + '/realTimeMonitoringController/exportItemRealTimeData';
    var param = "&deviceId=" + deviceId +
        "&deviceName=" + URLencode(URLencode(deviceName)) +
        '&calculateType=' + calculateType +
        "&itemName=" + URLencode(URLencode(itemName)) +
        '&itemCode=' + itemCode +
        '&itemType=' + itemType +
        '&itemResolutionMode=' + itemResolutionMode +
        '&itemBitIndex=' + itemBitIndex +
        '&key=' + key;

    exportDataMask(key, maskPanelId, loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}

function CreateItemRealtimeDataTable() {
    var selectRowId = "RealTimeMonitoringInfoDeviceListSelectRow_Id";
    var gridPanelId = "RealTimeMonitoringListGridPanel_Id";
    var deviceName = '';
    var deviceId = 0;
    var calculateType = 0;
    var itemName = Ext.getCmp("RealtimeDataItemName_Id").getValue();
    var itemCode = Ext.getCmp("RealtimeDataItemCode_Id").getValue();
    var itemType = Ext.getCmp("RealtimeDataItemType_Id").getValue();
    var itemResolutionMode = Ext.getCmp("RealtimeDataItemResolutionMode_Id").getValue();
    var itemBitIndex = Ext.getCmp("RealtimeDataItemBitIndex_Id").getValue();
    var selectRow = Ext.getCmp(selectRowId).getValue();
    if (Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length > 0) {
        calculateType = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
        deviceId = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
        deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
    }

    if (itemRealtimeDataHandsontableHelper != null) {
        if (itemRealtimeDataHandsontableHelper.hot != undefined) {
            itemRealtimeDataHandsontableHelper.hot.destroy();
        }
        itemRealtimeDataHandsontableHelper = null;
    }

    if (Ext.getCmp("ItemRealtimeDataPanel_Id") != undefined) {
        Ext.getCmp("ItemRealtimeDataPanel_Id").el.mask(loginUserLanguageResource.loadingData).show();
    }
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/realTimeMonitoringController/getItemRealTimeData',
        success: function (response) {
            if (isNotVal(Ext.getCmp("ItemRealtimeDataPanel_Id"))) {
                Ext.getCmp("ItemRealtimeDataPanel_Id").getEl().unmask();
            }

            var result = Ext.JSON.decode(response.responseText);

            updateTotalRecords(result.totalCount, "ItemRealtimeDataCount_Id");

            if (itemRealtimeDataHandsontableHelper == null || itemRealtimeDataHandsontableHelper.hot == undefined) {
                itemRealtimeDataHandsontableHelper = ItemRealtimeDataHandsontableHelper.createNew("ItemRealtimeDataDiv_Id");
                var colHeaders = [loginUserLanguageResource.acqTime, itemName];
                var columns = [{
                    data: 'acqTime'
                }, {
                    data: 'data'
                }];
                itemRealtimeDataHandsontableHelper.colHeaders = colHeaders;
                itemRealtimeDataHandsontableHelper.columns = columns;
                itemRealtimeDataHandsontableHelper.createTable(result.totalRoot);
            } else {
                itemRealtimeDataHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.getCmp("ItemRealtimeDataPanel_Id").getEl().unmask();
            Ext.MessageBox.alert(loginUserLanguageResource.error, loginUserLanguageResource.ajaxError);
        },
        params: {
            deviceName: deviceName,
            deviceId: deviceId,
            calculateType: calculateType,
            itemName: itemName,
            itemCode: itemCode,
            itemType: itemType,
            itemResolutionMode: itemResolutionMode,
            itemBitIndex: itemBitIndex
        }
    });
};

var ItemRealtimeDataHandsontableHelper = {
    createNew: function (divid) {
        var itemRealtimeDataHandsontableHelper = {};
        itemRealtimeDataHandsontableHelper.divid = divid;
        itemRealtimeDataHandsontableHelper.validresult = true;
        itemRealtimeDataHandsontableHelper.colHeaders = [];
        itemRealtimeDataHandsontableHelper.columns = [];

        itemRealtimeDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        }

        itemRealtimeDataHandsontableHelper.createTable = function (data) {
            if ($('#' + itemRealtimeDataHandsontableHelper.divid) != undefined && $('#' + itemRealtimeDataHandsontableHelper.divid)[0] != undefined) {
                $('#' + itemRealtimeDataHandsontableHelper.divid).empty();
                var hotElement = document.querySelector('#' + itemRealtimeDataHandsontableHelper.divid);
                itemRealtimeDataHandsontableHelper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    columns: itemRealtimeDataHandsontableHelper.columns,
                    stretchH: 'all',
                    rowHeaders: true,
                    colHeaders: itemRealtimeDataHandsontableHelper.colHeaders,
                    fixedColumnsStart: 1,
                    autoWrapRow: false,
                    columnSorting: true,
                    allowInsertRow: false,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    cells: function (row, col, prop) {
                        var cellProperties = {};
                        var visualRowIndex = this.instance.toVisualRow(row);
                        var visualColIndex = this.instance.toVisualColumn(col);
                        cellProperties.renderer = itemRealtimeDataHandsontableHelper.addCellStyle;
                        cellProperties.editor = false;
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function (event, coords, TD) {
                        if (coords.col >= 0 && coords.row >= 0 && itemRealtimeDataHandsontableHelper != null && itemRealtimeDataHandsontableHelper.hot != '' && itemRealtimeDataHandsontableHelper.hot != undefined && itemRealtimeDataHandsontableHelper.hot.getDataAtCell != undefined) {
                            var rawValue = itemRealtimeDataHandsontableHelper.hot.getDataAtCell(coords.row, coords.col);
                            if (isNotVal(rawValue)) {
                                var showValue = rawValue;
                                var rowChar = 90;
                                var maxWidth = rowChar * 10;
                                if (rawValue.length > rowChar) {
                                    showValue = '';
                                    let arr = [];
                                    let index = 0;
                                    while (index < rawValue.length) {
                                        arr.push(rawValue.slice(index, index += rowChar));
                                    }
                                    for (var i = 0; i < arr.length; i++) {
                                        showValue += arr[i];
                                        if (i < arr.length - 1) {
                                            showValue += '<br>';
                                        }
                                    }
                                }
                                if (!isNotVal(TD.tip)) {
                                    var height = 28;
                                    TD.tip = Ext.create('Ext.tip.ToolTip', {
                                        target: event.target,
                                        maxWidth: maxWidth,
                                        html: showValue,
                                        listeners: {
                                            hide: function (thisTip, eOpts) {},
                                            close: function (thisTip, eOpts) {}
                                        }
                                    });
                                } else {
                                    TD.tip.setHtml(showValue);
                                }
                            }
                        }
                    }
                });
            }
        }
        return itemRealtimeDataHandsontableHelper;
    }
};