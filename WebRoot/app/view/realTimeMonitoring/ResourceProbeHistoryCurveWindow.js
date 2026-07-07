Ext.define("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.resourceProbeHistoryCurveWindow',
    layout: 'fit',
    id:'ResourceProbeHistoryCurveWindow_Id',
    title: loginUserLanguageResource.trendCurve,
    border: false,
    hidden: false,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: '65%',
    height: '50%',
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	border: false,
        	items: [{
        		region: 'center',
        		layout: 'fit',
        		autoScroll: true,
        		border: false,
        		id:'ResourceProbeHistoryCurvePanel_Id',
        		tbar:[
            		{
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: loginUserLanguageResource.range,
                        labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                        format: 'Y-m-d',
                        id: 'ResourceProbeHistoryCurve_from_date_Id',
                        value: 'new',
                        editable:false,
                        listeners: {
                        	select: function (combo, record, index) {
//                        		Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'ResourceProbeHistoryCurveStartTime_Hour_Id',
                    	fieldLabel: loginUserLanguageResource.hour,
                        labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                        minValue: 0,
                        maxValue: 23,
                        value:'',
                        msgTarget: 'none',
                        regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'ResourceProbeHistoryCurveStartTime_Minute_Id',
                    	fieldLabel: loginUserLanguageResource.minute,
                        labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'ResourceProbeHistoryCurveStartTime_Second_Id',
                    	fieldLabel: loginUserLanguageResource.second,
                        labelWidth: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage)+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    }, {
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: loginUserLanguageResource.timeTo,
                        labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                        format: 'Y-m-d',
                        id: 'ResourceProbeHistoryCurve_end_date_Id',
                        value: '',
                        editable:false,
                        listeners: {
                        	select: function (combo, record, index) {
//                        		Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'ResourceProbeHistoryCurveEndTime_Hour_Id',
                    	fieldLabel: loginUserLanguageResource.hour,
                        labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                        minValue: 0,
                        maxValue: 23,
                        value:'',
                        msgTarget: 'none',
                        regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'ResourceProbeHistoryCurveEndTime_Minute_Id',
                    	fieldLabel: loginUserLanguageResource.minute,
                        labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'ResourceProbeHistoryCurveEndTime_Second_Id',
                    	fieldLabel: loginUserLanguageResource.second,
                        labelWidth: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage)+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },'-',{
                        xtype: 'button',
                        text: loginUserLanguageResource.search,
                        iconCls: 'search',
                        handler: function () {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('ResourceProbeHistoryCurveStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('ResourceProbeHistoryCurveStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('ResourceProbeHistoryCurveStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('ResourceProbeHistoryCurveEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('ResourceProbeHistoryCurveEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('ResourceProbeHistoryCurveEndTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                        }
                    },'->',{
                    	xtype: 'label',
                    	hidden: Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue()=="tableSpaceSize",
                    	html: loginUserLanguageResource.resourcesMonitoringHistoryCountTip+':<font color=red>'+resourceMonitoringSaveData+'</font>'
                    }
            	],
            	html: '<div id="ResourceProbeHistoryCurveDiv_Id" style="width:100%;height:100%;min-height:'+otherCardMinHeight+'px;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        if ($("#ResourceProbeHistoryCurveDiv_Id").highcharts() != undefined) {
                        	highchartsResize("ResourceProbeHistoryCurveDiv_Id");
                        }else{
                        	Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                        }
                    }
                }
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
                    var panel = Ext.getCmp('ResourceProbeHistoryCurvePanel_Id');
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
                    var panel = Ext.getCmp('ResourceProbeHistoryCurvePanel_Id');
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

                    win._panel = win.down('#ResourceProbeHistoryCurvePanel_Id');
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
                        var panel = Ext.getCmp('ResourceProbeHistoryCurvePanel_Id');
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
                            var panel = Ext.getCmp('ResourceProbeHistoryCurvePanel_Id');
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