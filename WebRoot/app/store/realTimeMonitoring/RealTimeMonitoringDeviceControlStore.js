// ===================== 全局辅助函数 =====================
window.handleControlButtonClick = function(btnEl) {
    if (btnEl.disabled || btnEl.classList.contains('x-btn-disabled')) {
        console.log('按钮禁用，不处理');
        return;
    }
    
    var index = parseInt(btnEl.getAttribute('data-index'), 10);
    var value = btnEl.getAttribute('data-value');
    var bitIndex = btnEl.getAttribute('data-bit-index');
    var showButtonCount = 9999;
    
    // 获取 Grid 组件
    var grid = Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
    if (!grid) {
        console.error('未找到 Grid 组件');
        return;
    }
    
    // 方法1：通过 grid 的 view 获取 record（最可靠）
    var record = null;
    var view = grid.getView();
    if (view && typeof view.getRecord === 'function') {
        record = view.getRecord(btnEl);
    }
    
    // 方法2：如果方法1失败，通过 DOM 行索引获取
    if (!record) {
        var rowEl = btnEl.closest('.x-grid-row');
        if (rowEl) {
            var rowIndex = rowEl.getAttribute('data-recordIndex');
            if (rowIndex !== null) {
                record = grid.getStore().getAt(parseInt(rowIndex, 10));
            }
        }
    }
    
    // 方法3：遍历所有行匹配（备用）
    if (!record) {
        var store = grid.getStore();
        var rows = view.getNodes();
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].contains(btnEl)) {
                record = store.getAt(i);
                break;
            }
        }
    }
    
    if (!record) {
        console.error('无法获取按钮对应的记录');
        return;
    }
    
    // 构造一个模拟的 widgetcolumn 容器对象
    var widgetColumnContainer = {
        getWidgetRecord: function() {
            return record;
        }
    };
    
    // 构造模拟按钮对象
    var mockBtn = {
        index: index,
        value: value,
        bitIndex: bitIndex || undefined,
        
        up: function(selector) {
            if (selector === undefined || selector === null) {
                return widgetColumnContainer;
            }
            if (selector === 'gridpanel' || selector === 'grid' || selector === 'tablepanel') {
                return grid;
            }
            return null;
        },
        
        getWidgetRecord: function() {
            return record;
        },
        
        getText: function() {
            var innerSpan = btnEl.querySelector('.x-btn-inner');
            return innerSpan ? innerSpan.innerText : btnEl.innerText;
        },
        
        setText: function(text) {
            var innerSpan = btnEl.querySelector('.x-btn-inner');
            if (innerSpan) innerSpan.innerText = text;
            else btnEl.innerText = text;
        },
        
        setDisabled: function(disabled) {
            if (disabled) {
                btnEl.disabled = true;
                btnEl.classList.add('x-btn-disabled');
            } else {
                btnEl.disabled = false;
                btnEl.classList.remove('x-btn-disabled');
            }
        },
        
        isDisabled: function() {
            return btnEl.disabled;
        },
        
        getAttribute: function(attr) {
            return btnEl.getAttribute(attr);
        },
        
        el: { dom: btnEl }
    };
    
    console.log('按钮点击，record:', record ? record.data : null);
    
    // 调用原有的 controlBtnHandler
    var handler = window.controlBtnHandler || controlBtnHandler;
    if (typeof handler === 'function') {
        try {
            handler(mockBtn, index, showButtonCount);
        } catch (err) {
            console.error('controlBtnHandler 执行错误:', err);
        }
    } else {
        console.error('controlBtnHandler 未定义！');
    }
};

// ===================== Store 定义 =====================
Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceControlStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RealTimeMonitoringDeviceControlStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceControlData',
        actionMethods: { read: 'POST' },
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, records, successful, operation, eOpts) {
            var rightPanel = Ext.getCmp("RealTimeMonitoringRightControlPanel");
            if (!rightPanel) return;
            
            var grid = Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
            if (!grid) {
                grid = Ext.create('Ext.grid.Panel', {
                    id: 'RealTimeMonitoringControlDataGridPanel_Id',
                    border: false,
                    columnLines: true,
                    forceFit: false,
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.controlItem,
                        dataIndex: 'item',
                        align: 'left',
                        width: '69%',
                        renderer: function (value, meta, record) {
                            meta.tdStyle = "vertical-align:middle;";
                            return value ? "<span data-qtip=\"" + Ext.util.Format.htmlEncode(value) + "\">" + Ext.util.Format.htmlEncode(value) + "</span>" : "";
                        }
                    }, {
                        text: loginUserLanguageResource.action,
                        align: 'center',
                        width: '30%',
                        renderer: function (value, meta, record) {
                        	var store = record.store;
                            var rawData = store.proxy.reader.rawData;
                            var isControl = rawData ? rawData.isControl : 0;
                            var commStatus = rawData ? rawData.commStatus : 0;
                            var showButtonCount = 9999;
                        	
                            var resolutionMode = record.data.resolutionMode;
                            var itemMeaning = record.data.itemMeaning || [];
                            var buttonsToShow = [];

                            if ((resolutionMode == 1 || resolutionMode == 0) && itemMeaning.length <= showButtonCount) {
                                for (var i = 0; i < itemMeaning.length; i++) {
                                    var btnText = resolutionMode == 1 ? itemMeaning[i][1] : itemMeaning[i].status;
                                    var btnValue = resolutionMode == 1 ? itemMeaning[i][0] : itemMeaning[i].value;
                                    buttonsToShow.push({
                                        text: btnText,
                                        value: btnValue,
                                        index: i,
                                        bitIndex: resolutionMode == 0 ? itemMeaning[i].bitIndex : undefined
                                    });
                                }
                            } else {
                                buttonsToShow.push({
                                    text: loginUserLanguageResource.set,
                                    value: '',
                                    index: 0
                                });
                            }

                            var disabled = !(commStatus > 0 && isControl == 1);
                            var btnsHtml = [];
                            Ext.Array.each(buttonsToShow, function (btnConfig) {
                                var btnClass = 'x-btn x-btn-default-small';
                                if (disabled) btnClass += ' x-btn-disabled';
                                btnsHtml.push(
                                    '<button class="' + btnClass + '" ' +
                                    'data-index="' + btnConfig.index + '" ' +
                                    'data-value="' + Ext.util.Format.htmlEncode(String(btnConfig.value)) + '" ' +
                                    'data-bit-index="' + (btnConfig.bitIndex !== undefined ? btnConfig.bitIndex : '') + '" ' +
                                    (disabled ? 'disabled="disabled"' : '') +
                                    ' onclick="window.handleControlButtonClick(this)" ' +
                                    ' style="height:24px; width:auto; min-width:60px; margin:2px auto; display:block;">' +
                                    '<span class="x-btn-inner">' + Ext.util.Format.htmlEncode(btnConfig.text) + '</span>' +
                                    '</button>'
                                );
                            });
                            return '<div style="text-align:center;">' + btnsHtml.join('') + '</div>';
                        }
                    }]
                });
                rightPanel.add(grid);
            } else {
            	grid.getView().refresh();
            }

            // 解除遮罩
            var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
            var infoPanel = Ext.getCmp("RealTimeMonitoringInfoPanel_Id");
            if (tabPanel) tabPanel.getEl().unmask();
            if (infoPanel) infoPanel.getEl().unmask();
        },

        beforeload: function (store, options) {
            var rightPanel = Ext.getCmp("RealTimeMonitoringRightControlPanel");
            if (!rightPanel) return;

            var selection = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0];
            if (selection) {
                var new_params = {
                    deviceId: selection.data.id,
                    deviceName: selection.data.deviceName,
                    deviceType: getDeviceTypeFromTabId("RealTimeMonitoringTabPanel")
                };
                Ext.apply(store.proxy.extraParams, new_params);
            }
        }
    }
});