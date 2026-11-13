
function historyDataComparativeAnalysisHexToHsl(hex) {
    if (!hex) return { h: 210, s: 70 };
    hex = hex.replace('#', '');
    if (hex.length === 3) hex = hex.split('').map(function (c) { return c + c; }).join('');
    var r = parseInt(hex.substr(0, 2), 16) / 255;
    var g = parseInt(hex.substr(2, 2), 16) / 255;
    var b = parseInt(hex.substr(4, 2), 16) / 255;
    var max = Math.max(r, g, b), min = Math.min(r, g, b);
    var h, s, l = (max + min) / 2;
    if (max === min) {
        h = s = 0;
    } else {
        var d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        switch (max) {
            case r: h = ((g - b) / d + (g < b ? 6 : 0)) / 6; break;
            case g: h = ((b - r) / d + 2) / 6; break;
            case b: h = ((r - g) / d + 4) / 6; break;
        }
    }
    return { h: Math.round(h * 360), s: Math.round(s * 100) };
}

function historyDataComparativeAnalysisContrastColor(hex) {
    if (!hex) return '#000000';
    hex = hex.replace('#', '');
    if (hex.length === 3) hex = hex.split('').map(function (c) { return c + c; }).join('');
    var r = parseInt(hex.substr(0, 2), 16);
    var g = parseInt(hex.substr(2, 2), 16);
    var b = parseInt(hex.substr(4, 2), 16);
    var brightness = (r * 299 + g * 587 + b * 114) / 1000;
    return brightness > 128 ? '#000000' : '#ffffff';
}

var historyDataComparativeAnalysisCurveConfig = {
    axis: {},   // key = itemColumn + "|" + dataSource
    series: {}  // key = itemColumn + "|" + dataSource + "_" + deviceId
};
var historyDataComparativeAnalysisLastResult = null;
var historyDataComparativeAnalysisTimeFormat = '%m-%d';

// ==========================================
// 画图（应用配置）
// ==========================================
function renderHistoryComparativeAnalysisCurve(result) {
    if (!result) return;
    var fieldList = result.items || [];
    var timeFormat = historyDataComparativeAnalysisTimeFormat;
    var BASE_HUES = [210, 0, 120, 45, 280, 190, 330, 90, 250, 20];
    var DASH_STYLES = ['Solid', 'Dash', 'Dot', 'DashDot', 'ShortDash', 'ShortDot'];
    var MAX_LIGHTNESS_LEVELS = 4;
    var L_MIN = 35, L_MAX = 72;

    var sortOrigIndexMap = {};
    for (var i = 0; i < fieldList.length; i++) {
        sortOrigIndexMap[fieldList[i].itemColumn + "|" + fieldList[i].dataSource] = i;
    }

    // 解析所有轴的配置
    var axisOrder = [];
    for (var i = 0; i < fieldList.length; i++) {
        var sort = fieldList[i].itemColumn + "|" + fieldList[i].dataSource;
        var cfg = historyDataComparativeAnalysisCurveConfig.axis[sort] || {};
        var pos = (cfg.axisSort !== undefined && cfg.axisSort !== '' && cfg.axisSort !== null) ? parseInt(cfg.axisSort) : (i + 1); 
        var opposite = (cfg.yAxisOpposite !== undefined && cfg.yAxisOpposite !== null) ? (cfg.yAxisOpposite === true || cfg.yAxisOpposite === 'true') : (i % 2 === 1);
        var unitSuffix = isNotVal(fieldList[i].itemUnit) ? ("(" + fieldList[i].itemUnit + ")") : "";
        axisOrder.push({
            sort: sort, pos: pos, origIndex: i, itemName: fieldList[i].itemName + unitSuffix, opposite: opposite, cfg: cfg
        });
    }

    // 分离左右轴
    var leftAxes = [];
    var rightAxes = [];
    for (var i = 0; i < axisOrder.length; i++) {
        if (axisOrder[i].opposite) rightAxes.push(axisOrder[i]);
        else leftAxes.push(axisOrder[i]);
    }
    leftAxes.sort(function(a, b) { return a.pos !== b.pos ? a.pos - b.pos : a.origIndex - b.origIndex; });
    rightAxes.sort(function(a, b) { return a.pos !== b.pos ? a.pos - b.pos : a.origIndex - b.origIndex; });

    // 构建 Y 轴配置
    var yAxis = [];
    var sortAxisMap = {};

    for (var i = leftAxes.length - 1; i >= 0; i--) {
        var f = leftAxes[i];
        var baseColor = f.cfg.axisColor ? '#' + f.cfg.axisColor : 'hsl(' + BASE_HUES[f.origIndex % BASE_HUES.length] + ',70%,50%)';
        sortAxisMap[f.sort] = yAxis.length;
        yAxis.push({
            title: { text: f.itemName, style: { color: baseColor } },
            labels: { style: { color: baseColor } },
            lineWidth: 1, tickWidth: 1, tickLength: 5, opposite: false
        });
    }
    for (var i = 0; i < rightAxes.length; i++) {
        var f = rightAxes[i];
        var baseColor = f.cfg.axisColor ? '#' + f.cfg.axisColor : 'hsl(' + BASE_HUES[f.origIndex % BASE_HUES.length] + ',70%,50%)';
        sortAxisMap[f.sort] = yAxis.length;
        yAxis.push({
            title: { text: f.itemName, style: { color: baseColor } },
            labels: { style: { color: baseColor } },
            lineWidth: 1, tickWidth: 1, tickLength: 5, opposite: true
        });
    }

    // 同字段设备数统计
    var fieldDeviceCount = {};
    for (var i = 0; i < result.list.length; i++) {
        var s = result.list[i].itemColumn + "|" + result.list[i].dataSource;
        fieldDeviceCount[s] = (fieldDeviceCount[s] || 0) + 1;
    }

    // series 组装
    var series = [];
    var fieldDeviceIndex = {};

    function buildSeriesForAxis(axisInfo) {
        var sort = axisInfo.sort;
        var axisIdx = sortAxisMap[sort];

        for (var i = 0; i < result.list.length; i++) {
            var row = result.list[i];
            var rowSort = row.itemColumn + "|" + row.dataSource;
            if (rowSort !== sort) continue;

            if (fieldDeviceIndex[rowSort] === undefined) fieldDeviceIndex[rowSort] = 0;
            var devIdx = fieldDeviceIndex[rowSort]++;
            var devCount = fieldDeviceCount[rowSort] || 1;
            
            var seriesKey = rowSort + '_' + row.deviceId;
            var sCfg = historyDataComparativeAnalysisCurveConfig.series[seriesKey] || {};

            // 颜色
            var color;
            if (sCfg.color) {
                color = '#' + sCfg.color;
            } else {
                var aCfg = historyDataComparativeAnalysisCurveConfig.axis[rowSort] || {};
                var hue, sat;
                if (aCfg.axisColor) {
                    var hsl = historyDataComparativeAnalysisHexToHsl(aCfg.axisColor);
                    hue = hsl.h; sat = hsl.s;
                } else {
                    hue = BASE_HUES[sortOrigIndexMap[rowSort] % BASE_HUES.length];
                    sat = 70;
                }
                var levelCount = Math.min(devCount, MAX_LIGHTNESS_LEVELS);
                var levelIdx = devIdx % levelCount;
                var lightness = (levelCount <= 1) ? 50 : (L_MIN + (L_MAX - L_MIN) * (levelIdx / (levelCount - 1)));
                color = 'hsl(' + hue + ',' + sat + '%,' + Math.round(lightness) + '%)';
            }

            // 线宽
            var lineWidth = 2;
            if (sCfg.lineWidth !== undefined && sCfg.lineWidth !== '' && sCfg.lineWidth !== null) {
                lineWidth = parseFloat(sCfg.lineWidth);
            }

            // 线型
            var dashStyle;
            if (sCfg.dashStyle) {
                dashStyle = sCfg.dashStyle;
            } else if (devCount > MAX_LIGHTNESS_LEVELS) {
                dashStyle = DASH_STYLES[Math.floor(devIdx / MAX_LIGHTNESS_LEVELS) % DASH_STYLES.length];
            } else {
                dashStyle = 'Solid';
            }

            var unitSuffix = isNotVal(row.itemUnit) ? ("(" + row.itemUnit + ")") : "";
            var singleSeries = {
                name: row.deviceName + '.' + row.itemName + unitSuffix,
                color: color,
                dashStyle: dashStyle,
                sort: rowSort,
                deviceId: row.deviceId,
                type: 'spline',
                marker: { enabled: false },
                yAxis: axisIdx,
                lineWidth: lineWidth,
                data: []
            };

            for (var j = 0; j < row.acqTime.length; j++) {
                var ts = Date.parse(row.acqTime[j].replace(/-/g, '/'));
                var v = parseFloat(row.data[j]);
                singleSeries.data.push([ts, isNaN(v) ? null : v]);
            }
            series.push(singleSeries);
        }
    }

    var allAxesSorted = leftAxes.concat(rightAxes);
    allAxesSorted.sort(function(a, b) { return a.pos !== b.pos ? a.pos - b.pos : a.origIndex - b.origIndex; });
    for (var i = 0; i < allAxesSorted.length; i++) {
        buildSeriesForAxis(allAxesSorted[i]);
    }

    // 绘制图表
    var xTitle = loginUserLanguageResource.acqTime;
    initComparativeAnalysisCurveChartFn(
        series, 'HistoryDataComparativeAnalysisCurveDiv_Id', '对比曲线', '', xTitle, yAxis, true, timeFormat
    );

    // 绑定双击事件
    var container = document.getElementById('HistoryDataComparativeAnalysisCurveDiv_Id');
    if (container && !container._dblclickBound) {
        container._dblclickBound = true;
        container.addEventListener('dblclick', function () {
            if (!historyDataComparativeAnalysisLastResult) return;
            var exist = Ext.getCmp('HistoryDataComparativeAnalysisCurveConfigWindow_Id');
            if (exist) exist.close();
            var win = Ext.create('AP.view.historyQuery.HistoryDataComparativeAnalysisCurveConfigWindow');
            win.show();
        });
    }
}

Ext.define("AP.view.historyQuery.HistoryDataComparativeAnalysisWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.historyDataComparativeAnalysisWindow',
    layout: 'fit',
    id:'HistoryDataComparativeAnalysisWindow_Id',
    title: loginUserLanguageResource.historyQuery+'/'+loginUserLanguageResource.comparativeAnalysis,
    iframe: true,
    width: '80%',
    height: '80%',
    shadow: 'sides',
    closable: false,          
    maximizable: false,
    minimizable: false,
    collapsible: false,
    plain: true,
    modal: true,
    border: false,
    draggable: true, 
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.range,
                labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                format: 'Y-m-d',
                value: new Date(),
                editable:false,
                id: 'HistoryDataComparativeAnalysisDataStartDate_Id'
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataStartTime_Hour_Id',
                fieldLabel: loginUserLanguageResource.hour,
                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                minValue: 0, maxValue: 23, value:'0', msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataStartTime_Minute_Id',
            	fieldLabel: loginUserLanguageResource.minute,
                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                minValue: 0, maxValue: 59, value:'0', msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.timeTo,
                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                format: 'Y-m-d',
                value: new Date(),
                editable:false,
                id: 'HistoryDataComparativeAnalysisDataEndDate_Id'
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataEndTime_Hour_Id',
            	fieldLabel: loginUserLanguageResource.hour,
                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                minValue: 0, maxValue: 23, value:'23', msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataEndTime_Minute_Id',
                fieldLabel: loginUserLanguageResource.minute,
                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                minValue: 0, maxValue: 59, value:'59', msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/
            },{
                xtype: 'button',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () { 
                	getComparativeAnalysisCurveData(); 
                	Ext.getCmp('HistoryDataComparativeAnalysisCurvePanel_Id').setTitle(loginUserLanguageResource.curve + 
              	           '<span style="font-size: 12px; font-weight: normal; color: #888; margin-left: 10px; font-style: italic;">' +
             	           '(双击图形配置曲线样式)');
                }
            }],
        	layout: 'border',
        	items: [{
        		region: 'west', width:'45%', header:false, layout: 'border', split: true, collapsible: true,
                items:[{
                	region: 'west', width: '33%', border:false, split: true, collapsible: true, layout: 'fit',
                    title: loginUserLanguageResource.deviceList, id: 'HistoryDataComparativeAnalysisDeviceListPanel_Id'
                },{
                	region: 'center', layout: 'fit', border:false,
                    title: loginUserLanguageResource.dataColumn, id: 'HistoryDataComparativeAnalysisFieldListPanel_Id'
                }]
        	},{
        		region: 'center', header:false, layout: 'border', id: 'HistoryDataComparativeAnalysisPanel_Id', autoScroll: true,
        		items:[{
        			region: 'center', title: loginUserLanguageResource.curve, id: 'HistoryDataComparativeAnalysisCurvePanel_Id',
                    layout: 'fit', minHeight: otherCardMinHeight, autoScroll: false, border: false,
                	html: '<div id="HistoryDataComparativeAnalysisCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                        resize: function () {
                        	if ($("#HistoryDataComparativeAnalysisCurveDiv_Id").highcharts() != undefined) {
                        		highchartsResize("HistoryDataComparativeAnalysisCurveDiv_Id");
                        	}
                        }
                    }
        		}]
        	}],
            listeners: {
                maximize: function (win) {
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    if (win._panel) win._panel.show();
                    if (win._toolbar) win._toolbar.show();
                    var minBtn = win._minimizeBtn; var expandBtn = win._expandBtn;
                    if (minBtn) minBtn.style.display = 'inline-block';
                    if (expandBtn) expandBtn.style.display = 'none';
                    var maxBtn = win._maximizeBtn; var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'none';
                    if (restoreBtn) restoreBtn.style.display = 'inline-block';
                    var panel = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
                    if (panel) Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                },
                restore: function (win) {
                    if (win._minimized) {
                        win._minimized = false; win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        var minBtn = win._minimizeBtn; var expandBtn = win._expandBtn;
                        if (minBtn) minBtn.style.display = 'inline-block';
                        if (expandBtn) expandBtn.style.display = 'none';
                    }
                    var maxBtn = win._maximizeBtn; var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'inline-block';
                    if (restoreBtn) restoreBtn.style.display = 'none';
                    if (win._savedHeight && !win.maximized) { win.setHeight(win._savedHeight); win._savedHeight = null; }
                    win._minimized = false; win.setTitle(win.originalTitle);
                    var panel = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
                    if (panel) Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                },
                afterrender: function (panel) {
                    var win = panel;
                    win.originalTitle = win.title; win._savedHeight = null; win._minimized = false; win._defaultHeight = win.getHeight();
                    win._panel = win.down('#HistoryDataComparativeAnalysisWindow_Id');
                    win._toolbar = win.down('toolbar');
                    if (win._toolbar) win._toolbar.show();
                    var header = win.getHeader();
                    if (!header) return;
                    var headerEl = header.el.dom;
                    var btnContainer = document.createElement('div');
                    btnContainer.style.cssText = 'position:absolute; right:2px; top:0; height:100%;display:flex; align-items:center; gap:2px; z-index:10;padding:0 4px;';
                    headerEl.style.position = 'relative';
                    headerEl.appendChild(btnContainer);

                    function createButton(text, title, clickHandler, isClose) {
                        var btn = document.createElement('button');
                        btn.textContent = text; btn.title = title;
                        var defaultColor = '#404040';
                        btn.style.cssText = 'background:transparent; border:none; font-size:16px; font-weight:300;cursor:pointer; padding:0 8px;display:flex; align-items:center; justify-content:center; border-radius:2px;transition:background 0.15s, color 0.15s;color:' + defaultColor + '; touch-action:manipulation;font-family:sans-serif; line-height:1;min-width:30px; min-height:30px;';
                        btn.addEventListener('pointerdown', function(e) {
                            e.preventDefault(); e.stopPropagation();
                            if (isClose) { this.style.background = '#e81123'; this.style.color = '#ffffff'; }
                            else { this.style.background = 'rgba(0,0,0,0.15)'; }
                            clickHandler(e); this.releasePointerCapture(e.pointerId);
                        }, { passive: false });
                        btn.addEventListener('pointerup', function(e) { this.style.background = 'transparent'; this.style.color = defaultColor; }, { passive: false });
                        btn.addEventListener('pointerleave', function(e) { this.style.background = 'transparent'; this.style.color = defaultColor; }, { passive: false });
                        return btn;
                    }

                    var minBtn = createButton('─', '', function () {
                        win._savedHeight = win.getHeight();
                        var headerH = win.getHeader() ? win.getHeader().getHeight() : 30;
                        if (win._panel) win._panel.hide(); if (win._toolbar) win._toolbar.hide();
                        win.setHeight(headerH); win._minimized = true;
                        minBtn.style.display = 'none'; expandBtn.style.display = 'inline-block';
                    });
                    btnContainer.appendChild(minBtn); win._minimizeBtn = minBtn;

                    var expandBtn = createButton('⤢', '', function () {
                        if (win._savedHeight) { win.setHeight(win._savedHeight); win._savedHeight = null; }
                        else { win.setHeight(win._defaultHeight || '80%'); }
                        win._minimized = false; win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show(); if (win._toolbar) win._toolbar.show();
                        win.updateLayout(); expandBtn.style.display = 'none'; minBtn.style.display = 'inline-block';
                        var p = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
                        if (p) Ext.defer(function () { p.fireEvent('resize', p, p.getWidth(), p.getHeight()); }, 100);
                    });
                    expandBtn.style.display = 'none'; btnContainer.appendChild(expandBtn); win._expandBtn = expandBtn;

                    var maxBtn = createButton('☐', '', function () { if (!win.maximized) win.maximize(); });
                    btnContainer.appendChild(maxBtn); win._maximizeBtn = maxBtn;

                    var restoreBtn = createButton('⧉', '', function () { if (win.maximized) win.restore(); });
                    restoreBtn.style.display = 'none'; btnContainer.appendChild(restoreBtn); win._restoreBtn = restoreBtn;

                    var closeBtn = createButton('✕', '', function () { win.close(); }, true);
                    btnContainer.appendChild(closeBtn);

                    header.el.on('click', function (e) {
                        if (btnContainer.contains(e.target)) return;
                        if (win._minimized && !win.maximized) {
                            e.stopEvent();
                            if (win._savedHeight) { win.setHeight(win._savedHeight); win._savedHeight = null; }
                            else { win.setHeight(win._defaultHeight || '80%'); }
                            win._minimized = false; win.setTitle(win.originalTitle);
                            win.updateLayout(); if (win._panel) win._panel.show(); if (win._toolbar) win._toolbar.show();
                            expandBtn.style.display = 'none'; minBtn.style.display = 'inline-block';
                            var p = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
                            if (p) Ext.defer(function () { p.fireEvent('resize', p, p.getWidth(), p.getHeight()); }, 100);
                        }
                    });

                    win.on('destroy', function () { if (btnContainer.parentNode) btnContainer.parentNode.removeChild(btnContainer); });
                    
                    var gridPanel = Ext.getCmp("ComparativeAnalysisDeviceListGridPanel_Id");
                    if(isNotVal(gridPanel)){ gridPanel.getStore().load(); }
                    else{ Ext.create("AP.store.historyQuery.ComparativeAnalysisDeviceListStore"); }
                    
                    var gridPanel2 = Ext.getCmp("ComparativeAnalysisItemListGridPanel_Id");
                    if(isNotVal(gridPanel2)){ gridPanel2.getStore().load(); }
                    else{ Ext.create("AP.store.historyQuery.ComparativeAnalysisFieldListStore"); }
                }
            }
        });
        me.callParent(arguments);
    }
});

function getComparativeAnalysisCurveData() {
    var selectionDeviceRecord = Ext.getCmp("ComparativeAnalysisDeviceListGridPanel_Id").getSelectionModel().getSelection();
    var selectionItemRecord = Ext.getCmp("ComparativeAnalysisItemListGridPanel_Id").getSelectionModel().getSelection();

    if (selectionDeviceRecord.length > 0 && selectionItemRecord.length > 0) {
        var selectDeviceId = [];
        var selectDeviceName = [];
        selectionDeviceRecord.forEach(function (record) {
            selectDeviceId.push(record.data.deviceId);
            selectDeviceName.push(record.data.deviceName);
        });

        var items = [];
        var seenKey = {};
        selectionItemRecord.forEach(function (record) {
            var itemColumn = record.data.itemColumn;
            var dataSource = record.data.dataSource;
            if (!isNotVal(itemColumn)) return;
            var key = itemColumn + "|" + dataSource;
            if (!seenKey[key]) {
                seenKey[key] = true;
                items.push({
                    itemName: record.data.itemName,
                    itemColumn: itemColumn,
                    itemUnit: isNotVal(record.data.itemUnit) ? record.data.itemUnit : "",
                    dataSource: dataSource
                });
            }
        });
        if (items.length === 0) {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
            return;
        }

        var startDate = Ext.getCmp('HistoryDataComparativeAnalysisDataStartDate_Id') != undefined ? Ext.getCmp('HistoryDataComparativeAnalysisDataStartDate_Id').rawValue : "";
        var startTime_Hour = Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Hour_Id') != undefined ? Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Hour_Id').getValue() : "";
        var startTime_Minute = Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Minute_Id') != undefined ? Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Minute_Id').getValue() : "";
        var startTime_Second = 0;
        var endDate = Ext.getCmp('HistoryDataComparativeAnalysisDataEndDate_Id') != undefined ? Ext.getCmp('HistoryDataComparativeAnalysisDataEndDate_Id').rawValue : "";
        var endTime_Hour = Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Hour_Id') != undefined ? Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Hour_Id').getValue() : "";
        var endTime_Minute = Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Minute_Id') != undefined ? Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Minute_Id').getValue() : "";
        var endTime_Second = 0;

        var panelId = 'HistoryDataComparativeAnalysisPanel_Id';
        Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
        Ext.Ajax.request({
            method: 'POST',
            url: context + '/historyQueryController/getComparativeAnalysisCurveData',
            success: function (response) {
            	if (isNotVal(Ext.getCmp(panelId))) {
                    Ext.getCmp(panelId).getEl().unmask();
                }
                var result = Ext.JSON.decode(response.responseText);
                
                if (startDate.split(' ')[0] == endDate.split(' ')[0]) {
                    historyDataComparativeAnalysisTimeFormat = '%H:%M';
                } else {
                    historyDataComparativeAnalysisTimeFormat = '%m-%d';
                }

                historyDataComparativeAnalysisLastResult = result;
                renderHistoryComparativeAnalysisCurve(result);
            },
            failure: function () {
                if (isNotVal(Ext.getCmp(panelId))) {
                    Ext.getCmp(panelId).getEl().unmask();
                }
                Ext.MessageBox.alert(loginUserLanguageResource.error, loginUserLanguageResource.ajaxError);
            },
            params: {
                deviceNames: selectDeviceName.join(","),
                deviceIds: selectDeviceId.join(","),
                items: Ext.JSON.encode(items),
                startDate: getDateAndTime(startDate, startTime_Hour, startTime_Minute, startTime_Second),
                endDate: getDateAndTime(endDate, endTime_Hour, endTime_Minute, endTime_Second)
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
    }
}

function initComparativeAnalysisCurveChartFn(series, divId, title, subtitle, xtitle, yAxis,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId, type: 'spline', shadow: false, borderWidth: 0, zoomType: 'xy',
	            zooming: { mouseWheel: { enabled: false } }
	        },
	        time: { timezoneOffset: new Date().getTimezoneOffset() },
	        credits: { enabled: false },
	        title: { text: title, style: { fontSize: chartTitleFontSize } },
	        subtitle: { text: subtitle },
	        xAxis: {
	            type: 'datetime', title: { text: xtitle },
	            labels: {
	                formatter: function () { return this.axis.chart.time.dateFormat(timeFormat, this.value); },
	                autoRotation:true, rotation: -45
	            }
	        },
	        yAxis: yAxis,
	        tooltip: {
	            crosshairs: true, shared: true, style: { color: '#333333', fontSize: '12px', padding: '8px' },
	            dateTimeLabelFormats: {
	                millisecond: '%Y-%m-%d %H:%M:%S.%L', second: '%Y-%m-%d %H:%M:%S', minute: '%Y-%m-%d %H:%M',
	                hour: '%Y-%m-%d %H', day: '%Y-%m-%d', week: '%m-%d', month: '%Y-%m', year: '%Y'
	            }
	        },
	        exporting: {
	            enabled: true, fallbackToExportServer: false, filename: title,
	            menuItemDefinitions: {
	                chartConfig: {
        				text: loginUserLanguageResource.diagramSet,
        				onclick: function() {
        					var window = Ext.create("AP.view.historyQuery.HistoryCurveSetWindow", { title: loginUserLanguageResource.historyDiagramSet });
                            window.show();
        				}
        			}
	            },
	            buttons: {
	    	    	contextButton: {
	    	    		menuItems: ['viewFullscreen','printChart','separator','downloadPNG','downloadJPEG','downloadSVG','separator','downloadXLS']
	    	    		}
	    	    },
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
	        },
	        plotOptions: {
	            spline: { fillOpacity: 0.3, shadow: true }
	        },
	        legend: {
	            layout: 'horizontal', align: 'center', verticalAlign: 'bottom',
	            enabled: legend, borderWidth: 0, itemHiddenStyle: { textDecoration: 'none' }
	        },
	        series: series
	    });
	}
};