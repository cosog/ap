//全局配置（刷新浏览器才丢失）
var dailyDataComparativeAnalysisCurveConfig = {
    // key = sort（字段）
    axis: {
        // "3": { axisSort: 1, yAxisOpposite: false, axisColor: "ff0000" }
    },
    // key = sort + "_" + deviceId
    series: {
        // "3_481": { lineWidth: 3, color: "ff0000", dashStyle: "Solid" }
    }
};

//保存上一次查询结果，供配置变更时重绘
var dailyDataComparativeAnalysisLastResult = null;

//HEX → HSL，用于"父节点配了颜色、子节点没配"时按父色生成明度
function dailyDataComparativeAnalysisHexToHsl(hex) {
    if (!hex) return { h: 210, s: 70 };
    hex = hex.replace('#', '');
    if (hex.length === 3) {
        hex = hex.split('').map(function (c) { return c + c; }).join('');
    }
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

//根据背景色决定单元格文字用黑还是白
function dailyDataComparativeAnalysisContrastColor(hex) {
    if (!hex) return '#000000';
    hex = hex.replace('#', '');
    if (hex.length === 3) hex = hex.split('').map(function (c) { return c + c; }).join('');
    var r = parseInt(hex.substr(0, 2), 16);
    var g = parseInt(hex.substr(2, 2), 16);
    var b = parseInt(hex.substr(4, 2), 16);
    var brightness = (r * 299 + g * 587 + b * 114) / 1000;
    return brightness > 128 ? '#000000' : '#ffffff';
}

//画图（应用配置）
function renderDailyComparativeAnalysisCurve(result) {
    if (!result) return;
    var fieldList = result.items || [];
    var timeFormat = '%m-%d';
    var BASE_HUES = [210, 0, 120, 45, 280, 190, 330, 90, 250, 20];
    var DASH_STYLES = ['Solid', 'Dash', 'Dot', 'DashDot', 'ShortDash', 'ShortDot'];
    var MAX_LIGHTNESS_LEVELS = 4;
    var L_MIN = 35, L_MAX = 72;

    // 记录每个字段最原始的排序位置，防止更改轴顺序时颜色跟着乱变
    var sortOrigIndexMap = {};
    for (var i = 0; i < fieldList.length; i++) {
        sortOrigIndexMap[fieldList[i].sort] = i;
    }

    //解析所有轴的配置
    var axisOrder = [];
    for (var i = 0; i < fieldList.length; i++) {
        var sort = fieldList[i].sort;
        var cfg = dailyDataComparativeAnalysisCurveConfig.axis[sort] || {};
        
        //如果未手动配置序号，默认取当前下标+1（与配置窗口显示保持绝对一致）
        var pos = (cfg.axisSort !== undefined && cfg.axisSort !== '' && cfg.axisSort !== null)
            ? parseInt(cfg.axisSort) : (i + 1); 
            
        var opposite;
        if (cfg.yAxisOpposite !== undefined && cfg.yAxisOpposite !== null) {
            opposite = (cfg.yAxisOpposite === true || cfg.yAxisOpposite === 'true');
        } else {
            opposite = i % 2 === 1; // 默认交替
        }
        
        axisOrder.push({
            sort: sort,
            pos: pos,
            origIndex: i,
            itemName: fieldList[i].itemName,
            opposite: opposite,
            cfg: cfg
        });
    }

    //分离左右轴，各自按序号升序排列
    var leftAxes = [];
    var rightAxes = [];
    for (var i = 0; i < axisOrder.length; i++) {
        if (axisOrder[i].opposite) {
            rightAxes.push(axisOrder[i]);
        } else {
            leftAxes.push(axisOrder[i]);
        }
    }

    leftAxes.sort(function(a, b) {
        if (a.pos !== b.pos) return a.pos - b.pos; 
        return a.origIndex - b.origIndex;
    });
    rightAxes.sort(function(a, b) {
        if (a.pos !== b.pos) return a.pos - b.pos; 
        return a.origIndex - b.origIndex;
    });

    //构建 Y 轴配置
    var yAxis = [];
    var sortAxisMap = {};

    // 左侧轴：倒序 push
    // 因为左侧轴索引越大越靠左（外侧），为了让序号小的在最左，必须把序号大的先 push 进数组（去内侧）
    for (var i = leftAxes.length - 1; i >= 0; i--) {
        var f = leftAxes[i];
        var baseColor = f.cfg.axisColor
            ? '#' + f.cfg.axisColor
            : 'hsl(' + BASE_HUES[f.origIndex % BASE_HUES.length] + ',70%,50%)';
            
        sortAxisMap[f.sort] = yAxis.length;
        
        yAxis.push({
            title: { text: f.itemName, style: { color: baseColor } },
            labels: { style: { color: baseColor } },
            lineWidth: 1, tickWidth: 1, tickLength: 5,
            opposite: false
        });
    }

    // 右侧轴：正序 push
    // 因为右侧轴索引越小越靠内，为了让序号小的靠内，必须把序号小的先 push 进数组
    for (var i = 0; i < rightAxes.length; i++) {
        var f = rightAxes[i];
        var baseColor = f.cfg.axisColor
            ? '#' + f.cfg.axisColor
            : 'hsl(' + BASE_HUES[f.origIndex % BASE_HUES.length] + ',70%,50%)';
            
        sortAxisMap[f.sort] = yAxis.length;
        
        yAxis.push({
            title: { text: f.itemName, style: { color: baseColor } },
            labels: { style: { color: baseColor } },
            lineWidth: 1, tickWidth: 1, tickLength: 5,
            opposite: true
        });
    }

    // 同字段设备数统计 
    var fieldDeviceCount = {};
    for (var i = 0; i < result.list.length; i++) {
        var s = result.list[i].sort;
        fieldDeviceCount[s] = (fieldDeviceCount[s] || 0) + 1;
    }

    //series 组装
    var series = [];
    var fieldDeviceIndex = {};

    function buildSeriesForAxis(axisInfo) {
        var sort = axisInfo.sort;
        var axisIdx = sortAxisMap[sort];

        for (var i = 0; i < result.list.length; i++) {
            var row = result.list[i];
            if (row.sort !== sort) continue;

            if (fieldDeviceIndex[row.sort] === undefined) fieldDeviceIndex[row.sort] = 0;
            var devIdx = fieldDeviceIndex[row.sort]++;
            var devCount = fieldDeviceCount[row.sort] || 1;
            
            var seriesKey = row.sort + '_' + row.deviceId;
            var sCfg = dailyDataComparativeAnalysisCurveConfig.series[seriesKey] || {};

            // 颜色
            var color;
            if (sCfg.color) {
                color = '#' + sCfg.color;
            } else {
                var aCfg = dailyDataComparativeAnalysisCurveConfig.axis[row.sort] || {};
                var hue, sat;
                if (aCfg.axisColor) {
                    var hsl = dailyDataComparativeAnalysisHexToHsl(aCfg.axisColor);
                    hue = hsl.h; sat = hsl.s;
                } else {
                    hue = BASE_HUES[sortOrigIndexMap[row.sort] % BASE_HUES.length];
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

            var singleSeries = {
                name: row.deviceName + '.' + row.itemName,
                color: color,
                dashStyle: dashStyle,
                sort: row.sort,
                deviceId: row.deviceId,
                type: 'spline',
                marker: { enabled: false },
                yAxis: axisIdx,
                lineWidth: lineWidth,
                data: []
            };

            for (var j = 0; j < row.calDate.length; j++) {
                var ts = Date.parse(row.calDate[j].replace(/-/g, '/'));
                var v = parseFloat(row.data[j]);
                singleSeries.data.push([ts, isNaN(v) ? null : v]);
            }
            series.push(singleSeries);
        }
    }

    //合并所有轴，强制按全局配置序号升序排列
    var allAxesSorted = leftAxes.concat(rightAxes);
    allAxesSorted.sort(function(a, b) {
        if (a.pos !== b.pos) return a.pos - b.pos;
        return a.origIndex - b.origIndex;
    });

    for (var i = 0; i < allAxesSorted.length; i++) {
        buildSeriesForAxis(allAxesSorted[i]);
    }
    var title = '对比曲线';

    initDailyComparativeAnalysisCurveChartFn(
        series, 'DailyDataComparativeAnalysisCurveDiv_Id', title, '', '', yAxis, true, timeFormat
    );

    // 绑定双击事件（只绑一次）
    var container = document.getElementById('DailyDataComparativeAnalysisCurveDiv_Id');
    if (container && !container._dblclickBound) {
        container._dblclickBound = true;
        container.addEventListener('dblclick', function () {
            if (!dailyDataComparativeAnalysisLastResult) return;
            var exist = Ext.getCmp('DailyDataComparativeAnalysisCurveConfigWindow_Id');
            if (exist) exist.close();
            var win = Ext.create('AP.view.reportOut.DailyDataComparativeAnalysisCurveConfigWindow');
            win.show();
        });
    }
}

Ext.define("AP.view.reportOut.DailyDataComparativeAnalysisWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.dailyDataComparativeAnalysisWindow',
    layout: 'fit',
    id:'DailyDataComparativeAnalysisWindow_Id',
    title: loginUserLanguageResource.productionReport+'/'+loginUserLanguageResource.comparativeAnalysis,
    iframe: true,
    width: '80%',
    height: '80%',
    shadow: 'sides',
//    closeAction: 'destroy',
//    resizable: true,
//    collapsible: true,
//    constrain: true,
//    maximizable: true,
//    minimizable: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    plain: true,
    modal: true,
    border: false,
    draggable: true, // 是否可拖曳
    constrain: true,   //限制拖拽不超出父界面
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
//                value: new Date(),
                value: Ext.Date.add(new Date(), Ext.Date.DAY, -9),
                editable:false,
                id: 'DailyDataComparativeAnalysisDataStartDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                		
                	}
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.timeTo,
                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                format: 'Y-m-d',
                value: new Date(),
//                value: Ext.Date.add(new Date(), Ext.Date.DAY, 1),
                editable:false,
                id: 'DailyDataComparativeAnalysisDataEndDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                		
                	}
                }
            },{
                xtype: 'button',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                	getDailyComparativeAnalysisCurveData();
                	Ext.getCmp('DailyDataComparativeAnalysisCurvePanel_Id').setTitle(loginUserLanguageResource.curve + 
             	           '<span style="font-size: 12px; font-weight: normal; color: #888; margin-left: 10px; font-style: italic;">' +
            	           '(双击图形配置曲线样式)');
                }
            }],
        	layout: 'border',
        	items: [{
        		region: 'west',
            	width:'45%',
            	header:false,
            	layout: 'border',
            	split: true,
                collapsible: true,
                items:[{
                	region: 'west',
                    width: '33%',
                    border:false,
                    split: true,
                    collapsible: true,
                    layout: 'fit',
                    title: loginUserLanguageResource.deviceList,
                    id: 'DailyDataComparativeAnalysisDeviceListPanel_Id'
                },{
                	region: 'center',
                    layout: 'fit',
                    border:false,
                    title: loginUserLanguageResource.dataColumn,
                    id: 'DailyDataComparativeAnalysisFieldListPanel_Id'
                }]
        	},{
        		region: 'center',
        		header:false,
        		layout: 'border',
        		id: 'DailyDataComparativeAnalysisPanel_Id',
        		autoScroll: true,
        		items:[{
        			region: 'center',
        			title: loginUserLanguageResource.curve,
                    id: 'DailyDataComparativeAnalysisCurvePanel_Id',
                    layout: 'fit',
                    minHeight: otherCardMinHeight,   // 图表最小可视高度
                    autoScroll: false,                // 禁止内部滚动
                    border: false,
                	html: '<div id="DailyDataComparativeAnalysisCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if ($("#DailyDataComparativeAnalysisCurveDiv_Id").highcharts() != undefined) {
                        		highchartsResize("DailyDataComparativeAnalysisCurveDiv_Id");
                        	}
                        }
                    }
        		}
//        		,{
//        			region: 'east',
//                    width: '30%',
//                    layout: 'fit',
//                    border:false,
//                    hidden:true,
//                    title: loginUserLanguageResource.data,
//                    id: 'DailyDataComparativeAnalysisDataPanel_Id'
//        		}
        		]
        	}],
            listeners: {
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
                    var panel = Ext.getCmp('DailyDataComparativeAnalysisWindow_Id');
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
                    var panel = Ext.getCmp('DailyDataComparativeAnalysisWindow_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
                afterrender: function (panel) {
                    var win = panel;
                    win.originalTitle = win.title;
                    win._savedHeight = null;
                    win._minimized = false;
                    win._defaultHeight = win.getHeight();

                    win._panel = win.down('#DailyDataComparativeAnalysisWindow_Id');
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
                        var panel = Ext.getCmp('DailyDataComparativeAnalysisWindow_Id');
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
                            var panel = Ext.getCmp('DailyDataComparativeAnalysisWindow_Id');
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
                    
                    var gridPanel = Ext.getCmp("DailyComparativeAnalysisDeviceListGridPanel_Id");
                    if(isNotVal(gridPanel)){
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create("AP.store.reportOut.ComparativeAnalysisDeviceListStore");
                    }
                    
                    var gridPanel = Ext.getCmp("DailyComparativeAnalysisItemListGridPanel_Id");
                    if(isNotVal(gridPanel)){
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create("AP.store.reportOut.ComparativeAnalysisFieldListStore");
                    }
                }
            }
        });
        me.callParent(arguments);
    }
});

function getDailyComparativeAnalysisCurveData() {
    var selectionDeviceRecord = Ext.getCmp("DailyComparativeAnalysisDeviceListGridPanel_Id")
        .getSelectionModel().getSelection();
    var selectionItemRecord = Ext.getCmp("DailyComparativeAnalysisItemListGridPanel_Id")
        .getSelectionModel().getSelection();

    if (selectionDeviceRecord.length > 0 && selectionItemRecord.length > 0) {
        var selectDeviceId = [];
        var selectDeviceName = [];

        selectionDeviceRecord.forEach(function (record) {
            selectDeviceId.push(record.data.deviceId);
            selectDeviceName.push(record.data.deviceName);
        });

        var items = [];
        var seenSort = {};
        selectionItemRecord.forEach(function (record) {
            var sort = record.data.sort;
            if (sort !== undefined && sort !== null && sort !== '' && !seenSort[sort]) {
                seenSort[sort] = true;
                items.push({
                    itemName: record.data.headerName,
                    sort: sort
                });
            }
        });
        if (items.length === 0) {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
            return;
        }

        var startDate = Ext.getCmp('DailyDataComparativeAnalysisDataStartDate_Id') != undefined
            ? Ext.getCmp('DailyDataComparativeAnalysisDataStartDate_Id').rawValue : "";
        var endDate = Ext.getCmp('DailyDataComparativeAnalysisDataEndDate_Id') != undefined
            ? Ext.getCmp('DailyDataComparativeAnalysisDataEndDate_Id').rawValue : "";

        var panelId = 'DailyDataComparativeAnalysisPanel_Id';
        Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();

        Ext.Ajax.request({
            method: 'POST',
            url: context + '/reportDataMamagerController/getComparativeAnalysisCurveData',
            success: function (response) {
            	if (isNotVal(Ext.getCmp(panelId))) {
                    Ext.getCmp(panelId).getEl().unmask();
                }
                var result = Ext.JSON.decode(response.responseText);

                //保存 result 并交给统一画图函数（配置可重绘）
                dailyDataComparativeAnalysisLastResult = result;
                renderDailyComparativeAnalysisCurve(result);
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
                startDate: startDate,
                endDate: endDate
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
    }
}

function getDailyComparativeAnalysisCurveData2() {
    var selectionDeviceRecord = Ext.getCmp("DailyComparativeAnalysisDeviceListGridPanel_Id")
        .getSelectionModel().getSelection();
    var selectionItemRecord = Ext.getCmp("DailyComparativeAnalysisItemListGridPanel_Id")
        .getSelectionModel().getSelection();

    if (selectionDeviceRecord.length > 0 && selectionItemRecord.length > 0) {
        var selectDeviceId = [];
        var selectDeviceName = [];

        selectionDeviceRecord.forEach(function (record) {
            selectDeviceId.push(record.data.deviceId);
            selectDeviceName.push(record.data.deviceName);
        });

        var items = [];
        var seenSort = {};
        selectionItemRecord.forEach(function (record) {
            var sort = record.data.sort;
            if (sort !== undefined && sort !== null && sort !== '' && !seenSort[sort]) {
                seenSort[sort] = true;
                items.push({
                    itemName: record.data.headerName,
                    sort: sort
                });
            }
        });
        if (items.length === 0) {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
            return;
        }

        var startDate = Ext.getCmp('DailyDataComparativeAnalysisDataStartDate_Id') != undefined
            ? Ext.getCmp('DailyDataComparativeAnalysisDataStartDate_Id').rawValue : "";
        var endDate = Ext.getCmp('DailyDataComparativeAnalysisDataEndDate_Id') != undefined
            ? Ext.getCmp('DailyDataComparativeAnalysisDataEndDate_Id').rawValue : "";

        var panelId = 'DailyDataComparativeAnalysisPanel_Id';
        Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();

        Ext.Ajax.request({
            method: 'POST',
            url: context + '/reportDataMamagerController/getComparativeAnalysisCurveData',
            success: function (response) {
                if (isNotVal(Ext.getCmp(panelId))) {
                    Ext.getCmp(panelId).getEl().unmask();
                }
                var result = Ext.JSON.decode(response.responseText);
                var timeFormat = '%m-%d';

                //按字段建 yAxis，同字段共用一个 yAxis
                var fieldList = result.items || [];
                var sortAxisMap = {};
                var yAxis = [];
                for (var i = 0; i < fieldList.length; i++) {
                    sortAxisMap[fieldList[i].sort] = i;
                    yAxis.push({
                        title: { text: fieldList[i].itemName },
                        lineWidth: 1,
                        tickWidth: 1,
                        tickLength: 5,
                        opposite: i % 2 === 1
                    });
                }

                //按“设备 × 字段”建 series，yAxis 用 sortAxisMap[row.sort]
                var series = [];
                for (var i = 0; i < result.list.length; i++) {
                    var row = result.list[i];
                    var singleSeries = {
                        name: row.deviceName + "." + row.itemName,
                        sort: row.sort,
                        type: 'spline',
                        marker: { enabled: false },
                        yAxis: sortAxisMap[row.sort] || 0,
                        data: []
                    };
                    for (var j = 0; j < row.calDate.length; j++) {
                        singleSeries.data.push([
                            Date.parse(row.calDate[j].replace(/-/g, '/')),
                            row.data[j]
                        ]);
                    }
                    series.push(singleSeries);
                }

                var titleNames = [];
                for (var i = 0; i < fieldList.length; i++) {
                    titleNames.push(fieldList[i].itemName);
                }
                var title = titleNames.join(" / ") +
                    (loginUserLanguage.toUpperCase() == 'ZH_CN' ? "" : " ") +
                    loginUserLanguageResourceFirstLower.curve;

                initDailyComparativeAnalysisCurveChartFn(
                    series,
                    'DailyDataComparativeAnalysisCurveDiv_Id',
                    title, '', '', yAxis, true, timeFormat
                );
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
                startDate: startDate,
                endDate: endDate
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
    }
}

function initDailyComparativeAnalysisCurveChartFn(series, divId, title, subtitle, xtitle, yAxis,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
//		Highcharts.setOptions({
//	        global: {
//	            useUTC: false
//	        }
//	    });
		
		if ($("#"+divId).highcharts() != undefined) {
            $("#"+divId).highcharts().destroy();
        }

	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: false,
	            borderWidth: 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },
	        time: {
	            timezoneOffset: new Date().getTimezoneOffset()   // 用户本地时区
	        },
	        credits: {
	            enabled: false
	        },
	        title: {
	            text: title,
	            style: {
	            	fontSize: chartTitleFontSize
	            }
	        },
	        subtitle: {
	            text: subtitle
	        },
	        xAxis: {
	            type: 'datetime',
	            title: {
	                text: xtitle
	            },
	            labels: {
	                formatter: function () {
	                	return this.axis.chart.time.dateFormat(timeFormat, this.value);
	                },
	                autoRotation:true,//自动旋转
	                rotation: -45 //倾斜度，防止数量过多显示不全  
//	                step: 2
	            }
	        },
	        yAxis: yAxis,
	        tooltip: {
	            crosshairs: true, //十字准线
	            shared: true,
	            style: {
	                color: '#333333',
	                fontSize: '12px',
	                padding: '8px'
	            },
	            dateTimeLabelFormats: {
	                millisecond: '%Y-%m-%d %H:%M:%S.%L',
	                second: '%Y-%m-%d %H:%M:%S',
	                minute: '%Y-%m-%d %H:%M',
	                hour: '%Y-%m-%d %H',
	                day: '%Y-%m-%d',
	                week: '%m-%d',
	                month: '%Y-%m',
	                year: '%Y'
	            }
	        },
	        exporting: {
	            enabled: true,
	            fallbackToExportServer: false,
	            filename: title,
	            menuItemDefinitions: {
	                chartConfig: {
        				text: loginUserLanguageResource.diagramSet,
        				onclick: function() {
        					var window = Ext.create("AP.view.historyQuery.HistoryCurveSetWindow", {
                                title: loginUserLanguageResource.historyDiagramSet
                            });
                            window.show();
        				}
        			}
	            },
	            buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    },
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
	        },
	        plotOptions: {
	            spline: {
	                fillOpacity: 0.3,
	                shadow: true,
	                events: {
	                	legendItemClick: function(e){
//	                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//	                		return true;
	                	}
	                }
	            }
	        },
	        legend: {
	            layout: 'horizontal',//horizontal水平 vertical 垂直
	            align: 'center',  //left，center 和 right
	            verticalAlign: 'bottom',//top，middle 和 bottom
	            enabled: legend,
	            borderWidth: 0,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },
	        series: series
	    });
	}
};