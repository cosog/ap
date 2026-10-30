Ext.define("AP.view.historyQuery.HistoryDataComparativeAnalysisWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.historyDataComparativeAnalysisWindow',
    layout: 'fit',
    id:'HistoryDataComparativeAnalysisWindow_Id',
    title: loginUserLanguageResource.comparativeAnalysis,
    iframe: true,
    width: '95%',
    height: '60%',
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
                id: 'HistoryDataComparativeAnalysisDataStartDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                		
                	}
                }
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataStartTime_Hour_Id',
                fieldLabel: loginUserLanguageResource.hour,
                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                minValue: 0,
                maxValue: 23,
                value:'0',
                msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataStartTime_Minute_Id',
            	fieldLabel: loginUserLanguageResource.minute,
                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                minValue: 0,
                maxValue: 59,
                value:'0',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                			field.focus(true, 100);
                		}
                    }
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.timeTo,
                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                format: 'Y-m-d',
//                value: new Date(),
                value: Ext.Date.add(new Date(), Ext.Date.DAY, 1),
                editable:false,
                id: 'HistoryDataComparativeAnalysisDataEndDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                		
                	}
                }
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataEndTime_Hour_Id',
            	fieldLabel: loginUserLanguageResource.hour,
                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                minValue: 0,
                maxValue: 23,
                value:'0',
                msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'HistoryDataComparativeAnalysisDataEndTime_Minute_Id',
                fieldLabel: loginUserLanguageResource.minute,
                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                minValue: 0,
                maxValue: 59,
                value:'0',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                			field.focus(true, 100);
                		}
                    }
                }
            },{
                xtype: 'button',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                	var startTime_Hour=Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Hour_Id').getValue();
                	if(!r.test(startTime_Hour)){
                		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                		Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Minute=Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Minute_Id').getValue();
                	if(!r2.test(startTime_Minute)){
                		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                		Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	
                	var endTime_Hour=Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Hour_Id').getValue();
                	if(!r.test(endTime_Hour)){
                		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                		Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Minute=Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Minute_Id').getValue();
                	if(!r2.test(endTime_Minute)){
                		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                		Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	
                	getComparativeAnalysisCurveData();
                }
            }],
        	layout: 'border',
        	items: [{
        		region: 'west',
            	width:'35%',
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
                    id: 'HistoryDataComparativeAnalysisDeviceListPanel_Id'
                },{
                	region: 'center',
                    layout: 'fit',
                    border:false,
                    title: loginUserLanguageResource.dataColumn,
                    id: 'HistoryDataComparativeAnalysisFieldListPanel_Id'
                }]
        	},{
        		region: 'center',
        		header:false,
        		layout: 'border',
        		id: 'HistoryDataComparativeAnalysisPanel_Id',
        		items:[{
        			region: 'west',
                    width: '66%',
                    title: loginUserLanguageResource.curve,
                    id: 'HistoryDataComparativeAnalysisCurvePanel_Id',
                    layout: 'fit',
                    minHeight: otherCardMinHeight,   // 图表最小可视高度
                    split: true,
                    collapsible: true,
                    autoScroll: false,                // 禁止内部滚动
                    border: false,
                	html: '<div id="HistoryDataComparativeAnalysisCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if ($("#HistoryDataComparativeAnalysisCurveDiv_Id").highcharts() != undefined) {
                        		highchartsResize("HistoryDataComparativeAnalysisCurveDiv_Id");
                        	}
                        }
                    }
        		},{
        			region: 'center',
                    layout: 'fit',
                    border:false,
                    title: loginUserLanguageResource.data,
                    id: 'HistoryDataComparativeAnalysisDataPanel_Id'
        		}]
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
                    var panel = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
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
                    var panel = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
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

                    win._panel = win.down('#HistoryDataComparativeAnalysisWindow_Id');
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
                        var panel = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
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
                            var panel = Ext.getCmp('HistoryDataComparativeAnalysisWindow_Id');
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
                    
                    var gridPanel = Ext.getCmp("ComparativeAnalysisDeviceListGridPanel_Id");
                    if(isNotVal(gridPanel)){
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create("AP.store.historyQuery.ComparativeAnalysisDeviceListStore");
                    }
                    
                    var gridPanel = Ext.getCmp("ComparativeAnalysisItemListGridPanel_Id");
                    if(isNotVal(gridPanel)){
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create("AP.store.historyQuery.ComparativeAnalysisFieldListStore");
                    }
                }
            }
        });
        me.callParent(arguments);
    }
});

function getComparativeAnalysisCurveData(){
	var selectionDeviceRecord = Ext.getCmp("ComparativeAnalysisDeviceListGridPanel_Id").getSelectionModel().getSelection();
	var selectionItemRecord = Ext.getCmp("ComparativeAnalysisItemListGridPanel_Id").getSelectionModel().getSelection();
	if(selectionDeviceRecord.length>0 && selectionItemRecord.length>0){
		var selectDeviceId=[];
		var selectDeviceName=[];
		
		selectionDeviceRecord.forEach(function(record) {
			selectDeviceId.push(record.data.deviceId);
			selectDeviceName.push(record.data.deviceName);
	    });
		
		var itemName=selectionItemRecord[0].data.itemName;
		var itemColumn=selectionItemRecord[0].data.itemColumn;
		var itemUnit=selectionItemRecord[0].data.itemUnit;
		var dataSource=selectionItemRecord[0].data.dataSource;
		
		var startDate=Ext.getCmp('HistoryDataComparativeAnalysisDataStartDate_Id')!=undefined?Ext.getCmp('HistoryDataComparativeAnalysisDataStartDate_Id').rawValue:"";
		var startTime_Hour=Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Hour_Id')!=undefined?Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Hour_Id').getValue():"";
		var startTime_Minute=Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Minute_Id')!=undefined?Ext.getCmp('HistoryDataComparativeAnalysisDataStartTime_Minute_Id').getValue():"";
		var startTime_Second=0;
	    var endDate=Ext.getCmp('HistoryDataComparativeAnalysisDataEndDate_Id')!=undefined?Ext.getCmp('HistoryDataComparativeAnalysisDataEndDate_Id').rawValue:"";
	    var endTime_Hour=Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Hour_Id')!=undefined?Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Hour_Id').getValue():"";
		var endTime_Minute=Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Minute_Id')!=undefined?Ext.getCmp('HistoryDataComparativeAnalysisDataEndTime_Minute_Id').getValue():"";
		var endTime_Second=0;
		
		var panelId='HistoryDataComparativeAnalysisPanel_Id';
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
		Ext.Ajax.request({
			method:'POST',
			url:context + '/historyQueryController/getComparativeAnalysisCurveData',
			success:function(response) {
				if(isNotVal(Ext.getCmp(panelId))){
					Ext.getCmp(panelId).getEl().unmask();
				}
				
				var result =  Ext.JSON.decode(response.responseText);
				var timeFormat='%m-%d';
			    if(startDate.split(' ')[0]==endDate.split(' ')[0]){
				    timeFormat='%H:%M';
			    }
				
			    var title = itemName+ (loginUserLanguage.toUpperCase()=='ZH_CN'?"":" ") + loginUserLanguageResourceFirstLower.trendCurve;
			    var xTitle=loginUserLanguageResource.acqTime;
			    var series = [];
			    var yAxis= [];
			    var singleAxis={
		        		title: {
		                    text: itemName+(isNotVal(itemUnit)?("("+itemUnit+")"):"")
		                },
		                lineWidth: 1,
			        	tickWidth: 1,      // 刻度线宽度
		                tickLength: 5,     // 刻度线长度（可选）
		                opposite:false
		          };
			    yAxis.push(singleAxis);
			    
			    for (var i = 0; i < result.list.length; i++) {
			    	var maxValue=null;
			        var minValue=null;
			        var allPositive=true;//全部是非负数
			        var allNegative=true;//全部是负值
			        
			        var singleSeries={};
			        singleSeries.name=result.list[i].deviceName+"."+itemName+(isNotVal(itemUnit)?("("+itemUnit+")"):"");
			        singleSeries.code=itemColumn;
			        singleSeries.type='spline';
			        singleSeries.marker={enabled: false};
			        singleSeries.yAxis=0;
			        singleSeries.data=[];
			        
			        for (var j = 0; j < result.list[i].acqTime.length; j++) {
			        	var pointData=[];
			        	pointData.push(Date.parse(result.list[i].acqTime[j].replace(/-/g, '/')));
			        	pointData.push(result.list[i].data[j]);
			        	
			        	if(parseFloat(result.list[i].data[j])<0){
			            	allPositive=false;
			            }else if(parseFloat(result.list[i].data[j])>=0){
			            	allNegative=false;
			            }
			        	
			        	singleSeries.data.push(pointData);
			        }
			        series.push(singleSeries);
			    }
			    
			   
			    initComparativeAnalysisCurveChartFn(series, 'HistoryDataComparativeAnalysisCurveDiv_Id', title, '', '', yAxis,true,timeFormat);
			},
			failure:function(){
				if(isNotVal(Ext.getCmp(panelId))){
					Ext.getCmp(panelId).getEl().unmask();
				}
				Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
			},
			params: {
				deviceNames:selectDeviceName.join(","),
				deviceIds:selectDeviceId.join(","),
				itemName:itemName,
				itemColumn:itemColumn,
				itemUnit:itemUnit,
				dataSource:dataSource,
				startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
	            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
	        }
		});
	}else{
		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
	}
	
}

function initComparativeAnalysisCurveChartFn(series, divId, title, subtitle, xtitle, yAxis,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
//		Highcharts.setOptions({
//	        global: {
//	            useUTC: false
//	        }
//	    });

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
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
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
	    	    	    			'downloadXLS',
	    	    	    			'separator',
	    	    	    			'chartConfig'
	    	    	    			]
	    	    	    		}
	    	    	    }
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