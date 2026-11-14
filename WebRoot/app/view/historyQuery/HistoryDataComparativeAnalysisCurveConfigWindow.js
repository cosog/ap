//HSL 转 HEX 
function historyDataComparativeAnalysisHslToHex(h, s, l) {
    l /= 100;
    const a = s * Math.min(l, 1 - l) / 100;
    const f = n => {
        const k = (n + h / 30) % 12;
        const color = l - a * Math.max(Math.min(k - 3, 9 - k, 1), -1);
        return Math.round(255 * color).toString(16).padStart(2, '0');
    };
    return `${f(0)}${f(8)}${f(4)}`;
}

Ext.define('AP.view.historyQuery.HistoryDataComparativeAnalysisCurveConfigWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.historyDataComparativeAnalysisCurveConfigWindow',
    layout: 'fit',
    id: 'HistoryDataComparativeAnalysisCurveConfigWindow_Id',
    title: loginUserLanguageResource.curveProperty + 
    '<span style="font-size: 12px; font-weight: normal; color: #888; margin-left: 10px; font-style: italic;">' +
    '(双击节点配置Y轴或曲线)',
    width: 400,
    height: '70%',
    modal: true,
    border: false,
    closable: true,
    constrain: true,
    draggable: true,
    resizable: true,
    maximizable: false,
    minimizable: false,

    initComponent: function () {
        var me = this;

        var store = Ext.create('Ext.data.TreeStore', {
            root: { expanded: true, children: [] },
            fields: [
                'type', 'fieldKey', 'deviceId', 'seriesKey', 'name',
                'configStr', 'fullConfigStr',
                'axisSortValue', 'oppositeValue', 'axisColorValue',
                'lineWidthValue', 'seriesColorValue', 'dashStyleValue'
            ]
        });

        me.buildTreeData = function () {
            var result = historyDataComparativeAnalysisLastResult;
            var treeData = [];
            if (!result) return treeData;
            
            var fieldList = result.items || [];
            var BASE_HUES = [210, 0, 120, 45, 280, 190, 330, 90, 250, 20];
            var MAX_LIGHTNESS_LEVELS = 4;
            var L_MIN = 35, L_MAX = 72;

            var fieldDeviceCount = {};
            for (var k = 0; k < result.list.length; k++) {
                var r = result.list[k];
                var key = r.itemColumn + "|" + r.dataSource;
                fieldDeviceCount[key] = (fieldDeviceCount[key] || 0) + 1;
            }

            for (var i = 0; i < fieldList.length; i++) {
                var f = fieldList[i];
                var fieldKey = f.itemColumn + "|" + f.dataSource;
                var cfg = historyDataComparativeAnalysisCurveConfig.axis[fieldKey] || {};
                
                var defaultAxisSort = i + 1;
                var defaultOpposite = i % 2 === 1;
                
                var defaultAxisHex = historyDataComparativeAnalysisHslToHex(BASE_HUES[i % BASE_HUES.length], 70, 50);

                var axisSort = (cfg.axisSort !== undefined && cfg.axisSort !== null && cfg.axisSort !== '') ? cfg.axisSort : defaultAxisSort;
                var opposite = (cfg.yAxisOpposite !== undefined && cfg.yAxisOpposite !== null) ? cfg.yAxisOpposite : defaultOpposite;
                var oppositeText = opposite ? loginUserLanguageResource.right : loginUserLanguageResource.left;
                
                var axisColor = cfg.axisColor || defaultAxisHex;

                var configStr = axisSort + ';' + oppositeText + ';' + axisColor;
                var fullConfigStr = JSON.stringify({
                    axisSort: axisSort,
                    yAxisOpposite: opposite,
                    axisColor: axisColor
                });

                var unitSuffix = isNotVal(f.itemUnit) ? ("(" + f.itemUnit + ")") : "";
                var node = {
                    leaf: false,
                    expanded: true,
                    type: 'axis',
                    fieldKey: fieldKey,
                    name: f.itemName + unitSuffix,
                    configStr: configStr,
                    fullConfigStr: fullConfigStr,
                    axisSortValue: axisSort,
                    oppositeValue: opposite,
                    axisColorValue: axisColor,
                    children: []
                };

                var devIdx = 0;
                var devCount = fieldDeviceCount[fieldKey] || 1;
                
                for (var j = 0; j < result.list.length; j++) {
                    var row = result.list[j];
                    var rowKey = row.itemColumn + "|" + row.dataSource;
                    if (rowKey != fieldKey) continue;
                    
                    var seriesKey = rowKey + '_' + row.deviceId;
                    var sCfg = historyDataComparativeAnalysisCurveConfig.series[seriesKey] || {};

                    var hsl = historyDataComparativeAnalysisHexToHsl(axisColor);
                    var levelCount = Math.min(devCount, MAX_LIGHTNESS_LEVELS);
                    var levelIdx = devIdx % levelCount;
                    var lightness = (levelCount <= 1) ? 50 : (L_MIN + (L_MAX - L_MIN) * (levelIdx / (levelCount - 1)));
                    
                    var defaultSeriesHex = historyDataComparativeAnalysisHslToHex(hsl.h, hsl.s, Math.round(lightness));

                    var seriesColor = sCfg.color || defaultSeriesHex;
                    var seriesFullCfg = {
                        lineWidth: sCfg.lineWidth || '',
                        color: seriesColor,
                        dashStyle: sCfg.dashStyle || 'Solid'
                    };

                    var rowUnitSuffix = isNotVal(row.itemUnit) ? ("(" + row.itemUnit + ")") : "";
                    node.children.push({
                        leaf: true,
                        type: 'series',
                        fieldKey: fieldKey,
                        deviceId: row.deviceId,
                        seriesKey: seriesKey,
                        name: row.deviceName + '.' + row.itemName + rowUnitSuffix,
                        configStr: seriesColor,
                        fullConfigStr: JSON.stringify(seriesFullCfg),
                        lineWidthValue: (sCfg.lineWidth !== undefined && sCfg.lineWidth !== '') ? sCfg.lineWidth : 2, 
                        seriesColorValue: seriesColor,
                        dashStyleValue: sCfg.dashStyle || ''
                    });
                    
                    devIdx++;
                }
                treeData.push(node);
            }
            return treeData;
        };

        store.setRootNode({ expanded: true, children: me.buildTreeData() });

        var treePanel = Ext.create('Ext.tree.Panel', {
            store: store,
            rootVisible: false,
            useArrows: true,
            border: false,
            singleExpand: false,
            viewConfig: {
                toggleOnDblClick: false
            },
            columns: [{
                xtype: 'treecolumn',
                text: loginUserLanguageResource.name || '名称',
                dataIndex: 'name',
                flex: 2
            }, {
                text: loginUserLanguageResource.config || '配置',
                dataIndex: 'configStr',
                flex: 1,
                renderer: function (value, metaData, record) {
                    var color = '';
                    if (record.get('type') === 'axis') {
                        color = record.get('axisColorValue') || '';
                    } else {
                        color = record.get('seriesColorValue') || '';
                    }
                    if (color) {
                        metaData.tdStyle = 'background-color:#' + color
                            + ';color:' + historyDataComparativeAnalysisContrastColor(color) + ';';
                    }
                    return value || '';
                }
            }, {
                text: loginUserLanguageResource.allConfig || '全部配置信息',
                dataIndex: 'fullConfigStr',
                flex: 2,
                hidden: true
            }],
            listeners: {
                itemdblclick: function (view, record, item, index, e, eOpts) {
                    me.openCurvePropertyWindow(record);
                }
            }
        });

        me.treePanel = treePanel;

        me.refreshTree = function () {
            if (!me.treePanel) return;
            me.treePanel.getStore().setRootNode({
                expanded: true,
                children: me.buildTreeData()
            });
        };

        me.repaintChart = function () {
            if (historyDataComparativeAnalysisLastResult) {
                renderHistoryComparativeAnalysisCurve(historyDataComparativeAnalysisLastResult);
            }
        };

        me.openAxisConfigWindow = function (record) {
            var exist = Ext.getCmp('HistoryDataComparativeAnalysisAxisConfigWindow_Id');
            if (exist) exist.close();

            var win = Ext.create('Ext.window.Window', {
                id: 'HistoryDataComparativeAnalysisAxisConfigWindow_Id',
                title: loginUserLanguageResource.curveProperty,
                width: 360, modal: true, border: false, constrain: true, layout: 'fit', closeAction: 'destroy',
                items: [{
                    xtype: 'form', border: false, bodyStyle: 'padding:10px;', defaultType: 'textfield',
                    items: [{
                        xtype: 'numberfield', id: 'HistoryDataComparativeAnalysisAxisSort_Id',
                        fieldLabel: loginUserLanguageResource.curveSort, minValue: 1, allowBlank: true, editable: false, anchor: '100%',
                        value: record.get('axisSortValue') || ''
                    }, {
                        xtype: 'combobox', id: 'HistoryDataComparativeAnalysisAxisOpposite_Id',
                        fieldLabel: loginUserLanguageResource.yAxisPosition, editable: false, forceSelection: true, allowBlank: false, anchor: '100%',
                        store: new Ext.data.SimpleStore({
                            fields: ['value', 'text'],
                            data: [[false, loginUserLanguageResource.left], [true, loginUserLanguageResource.right]]
                        }),
                        displayField: 'text', valueField: 'value', queryMode: 'local',
                        value: (record.get('oppositeValue') === true || record.get('oppositeValue') === 'true')
                    }, {
                        id: 'HistoryDataComparativeAnalysisAxisColor_Id', xtype: 'colorfield',
                        fieldLabel: 'Y轴颜色', allowBlank: true, editable: false, anchor: '100%', value: record.get('axisColorValue') || '',
                        listeners: {
                            afterrender: function (field) { var val = field.getValue(); if (val) field.inputEl.applyStyles({ background: '#' + val }); },
                            collapse: function (field) { field.setValue(field.value); field.inputEl.applyStyles({ background: '#' + field.value }); }
                        }
                    }]
                }],
                buttons: [{
                    xtype: 'button', text: loginUserLanguageResource.confirm, iconCls: 'save',
                    handler: function () {
                        var axisSort = win.down('#HistoryDataComparativeAnalysisAxisSort_Id').getValue();
                        var opposite = win.down('#HistoryDataComparativeAnalysisAxisOpposite_Id').getValue();
                        var color = win.down('#HistoryDataComparativeAnalysisAxisColor_Id').getValue();
                        var key = record.get('fieldKey');
                        var cfg = historyDataComparativeAnalysisCurveConfig.axis[key] || {};
                        cfg.axisSort = axisSort;
                        cfg.yAxisOpposite = (opposite === true || opposite === 'true');
                        cfg.axisColor = color || '';
                        historyDataComparativeAnalysisCurveConfig.axis[key] = cfg;
                        win.close(); me.refreshTree(); me.repaintChart();
                    }
                }, {
                    xtype: 'button', text: loginUserLanguageResource.clear || '清除', hidden:true,
                    handler: function () {
                        delete historyDataComparativeAnalysisCurveConfig.axis[record.get('fieldKey')];
                        win.close(); me.refreshTree(); me.repaintChart();
                    }
                }, {
                    xtype: 'button', text: loginUserLanguageResource.cancel, iconCls: 'cancel',
                    handler: function () { win.close(); }
                }]
            });
            win.show();
        };

        me.openSeriesConfigWindow = function (record) {
            var exist = Ext.getCmp('HistoryDataComparativeAnalysisSeriesConfigWindow_Id');
            if (exist) exist.close();

            var win = Ext.create('Ext.window.Window', {
                id: 'HistoryDataComparativeAnalysisSeriesConfigWindow_Id',
                title: loginUserLanguageResource.curveProperty,
                width: 360, modal: true, border: false, constrain: true, layout: 'fit', closeAction: 'destroy',
                items: [{
                    xtype: 'form', border: false, bodyStyle: 'padding:10px;', defaultType: 'textfield',
                    items: [{
                        xtype: 'numberfield', id: 'HistoryDataComparativeAnalysisSeriesLineWidth_Id',
                        fieldLabel: loginUserLanguageResource.lineWidth, minValue: 1, allowBlank: true, editable: false, anchor: '100%',
                        value: record.get('lineWidthValue') || 2
                    }, {
                        id: 'HistoryDataComparativeAnalysisSeriesColor_Id', xtype: 'colorfield',
                        fieldLabel: loginUserLanguageResource.curveColor, allowBlank: true, editable: false, anchor: '100%', value: record.get('seriesColorValue') || '',
                        listeners: {
                            afterrender: function (field) { var val = field.getValue(); if (val) field.inputEl.applyStyles({ background: '#' + val }); },
                            collapse: function (field) { field.setValue(field.value); field.inputEl.applyStyles({ background: '#' + field.value }); }
                        }
                    }, {
                        xtype: 'combobox', id: 'HistoryDataComparativeAnalysisSeriesDashStyle_Id',
                        fieldLabel: loginUserLanguageResource.lineDash, editable: false, forceSelection: true, allowBlank: true, anchor: '100%',
                        store: new Ext.data.SimpleStore({
                            fields: ['value', 'text'],
                            data: [
                                ['Solid', 'Solid'],['ShortDash', 'ShortDash'],['ShortDot', 'ShortDot'],['ShortDashDot', 'ShortDashDot'],['ShortDashDotDot', 'ShortDashDotDot'],['Dot', 'Dot'],['Dash', 'Dash'],['LongDash', 'LongDash'],['DashDot', 'DashDot'],['LongDashDot', 'LongDashDot'],['LongDashDotDot', 'LongDashDotDot']
                            ]
                        }),
                        displayField: 'text', valueField: 'value', queryMode: 'local',
                        emptyText: loginUserLanguageResource.default || '默认', value: record.get('dashStyleValue') || ''
                    }]
                }],
                buttons: [{
                    xtype: 'button', text: loginUserLanguageResource.confirm, iconCls: 'save',
                    handler: function () {
                        var lineWidth = win.down('#HistoryDataComparativeAnalysisSeriesLineWidth_Id').getValue();
                        var color = win.down('#HistoryDataComparativeAnalysisSeriesColor_Id').getValue();
                        var dashStyle = win.down('#HistoryDataComparativeAnalysisSeriesDashStyle_Id').getValue();
                        var key = record.get('seriesKey');
                        var cfg = historyDataComparativeAnalysisCurveConfig.series[key] || {};
                        cfg.lineWidth = lineWidth || 2; 
                        cfg.color = color || '';
                        cfg.dashStyle = dashStyle || '';
                        historyDataComparativeAnalysisCurveConfig.series[key] = cfg;
                        win.close(); me.refreshTree(); me.repaintChart();
                    }
                }, {
                    xtype: 'button', text: loginUserLanguageResource.clear || '清除', hidden:true,
                    handler: function () {
                        delete historyDataComparativeAnalysisCurveConfig.series[record.get('seriesKey')];
                        win.close(); me.refreshTree(); me.repaintChart();
                    }
                }, {
                    xtype: 'button', text: loginUserLanguageResource.cancel, iconCls: 'cancel',
                    handler: function () { win.close(); }
                }]
            });
            win.show();
        };

        me.openCurvePropertyWindow = function (record) {
            if (record.get('type') === 'axis') {
                me.openAxisConfigWindow(record);
            } else {
                me.openSeriesConfigWindow(record);
            }
        };

        Ext.apply(me, {
            items: [treePanel],
            buttons: [{
                xtype: 'button', text: loginUserLanguageResource.close || '关闭', iconCls: 'cancel',
                handler: function () { me.close(); }
            }]
        });

        me.callParent(arguments);
    }
});