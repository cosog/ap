Ext.define("AP.view.realTimeMonitoring.ItemRealtimeCurveWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemRealtimeCurveWindow',
    layout: 'fit',
    id:'ItemRealtimeCurveWindow_Id',
    title: loginUserLanguageResource.trendCurve,
    border: false,
    hidden: false,
    constrainHeader:true,
    closable: false,
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: '65%',
    height: '50%',
    draggable: true,
    modal: true,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	items: [{
        		region: 'center',
        		layout: 'fit',
        		autoScroll: true,
        		border: false,
        		tbar:[
        		    { id: 'RealtimeCurveItemName_Id',              xtype: 'textfield', value: '', hidden: true },
        		    { id: 'RealtimeCurveItemCode_Id',              xtype: 'textfield', value: '', hidden: true },
        		    { id: 'RealtimeCurveItemType_Id',              xtype: 'textfield', value: '', hidden: true },
        		    { id: 'RealtimeCurveItemResolutionMode_Id',    xtype: 'textfield', value: '', hidden: true },
        		    // ★ 新增：用于"场景模型标注双击"时携带绑定设备信息
        		    //   从实时监控动态数据表进入时这些字段为空，函数内部会回退到旧逻辑
        		    { id: 'RealtimeCurveDeviceId_Id',              xtype: 'textfield', value: '', hidden: true },
        		    { id: 'RealtimeCurveDeviceName_Id',            xtype: 'textfield', value: '', hidden: true },
        		    { id: 'RealtimeCurveCalculateType_Id',         xtype: 'textfield', value: '', hidden: true }
        		],
                items:[{
                	xtype: 'panel',
                    layout: 'fit',
                    minHeight: otherCardMinHeight,
                    autoScroll: false,
                    border: false,
                    id: 'ItemRealtimeCurvePanel_Id',
                	html: '<div id="ItemRealtimeCurveContainer" class="hbox" style="width:100%;height:100%;display:flex;flex-wrap:wrap;align-content:flex-start;"></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	var chartCreated=false;
                        	var container=$('#ItemRealtimeCurveContainer');
                			if(container!=undefined && container.length>0){
                				var containerChildren=container[0].children;
                				if(containerChildren!=undefined && containerChildren.length>0){
                					for(var i=0;i<containerChildren.length;i++){
                						var chart = $("#"+containerChildren[i].id).highcharts(); 
                						if(isNotVal(chart)){
                							chartCreated=true;
                							highchartsResize(containerChildren[i].id);
                						}
                					}
                				}
                			}
                			if(!chartCreated){
                				getItemRealTimeCurveData();
                			}
                        }
                    }
                }]
        	}],
            listeners: {
                beforeclose: function (panel) {
                	
                },
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
                    var panel = Ext.getCmp('ItemRealtimeCurvePanel_Id');
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
                    var panel = Ext.getCmp('ItemRealtimeCurvePanel_Id');
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

                    win._panel = win.down('#ItemRealtimeCurvePanel_Id');
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

                        btn.addEventListener('pointerdown', function(e) {
                            e.preventDefault();
                            e.stopPropagation();
                            if (isClose) {
                                this.style.background = '#e81123';
                                this.style.color = '#ffffff';
                            } else {
                                this.style.background = 'rgba(0,0,0,0.15)';
                            }
                            clickHandler(e);
                            this.releasePointerCapture(e.pointerId);
                        }, { passive: false });

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
                        var panel = Ext.getCmp('ItemRealtimeCurvePanel_Id');
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
                            var panel = Ext.getCmp('ItemRealtimeCurvePanel_Id');
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

function getItemRealTimeCurveData(){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var panelId="ItemRealtimeCurveWindow_Id";
	var deviceName='';
	var deviceId='';
	var calculateType='';
	var itemName=Ext.getCmp("RealtimeCurveItemName_Id").getValue();
	var itemCode=Ext.getCmp("RealtimeCurveItemCode_Id").getValue();
	var itemType=Ext.getCmp("RealtimeCurveItemType_Id").getValue();
	var itemResolutionMode=Ext.getCmp("RealtimeCurveItemResolutionMode_Id").getValue();

	// ★ 优先读窗口隐藏字段（场景模型标注双击时写入）
	//   为空时回退到实时表选中行（旧路径，兼容原行为）
	var devIdField = Ext.getCmp("RealtimeCurveDeviceId_Id");
	var devNameField = Ext.getCmp("RealtimeCurveDeviceName_Id");
	var calcTypeField = Ext.getCmp("RealtimeCurveCalculateType_Id");
	if (devIdField && isNotVal(devIdField.getValue())) {
		deviceId = devIdField.getValue();
		deviceName = (devNameField && isNotVal(devNameField.getValue())) ? devNameField.getValue() : '';
		calculateType = (calcTypeField && isNotVal(calcTypeField.getValue())) ? calcTypeField.getValue() : 0;
	}
	if (!isNotVal(deviceId) || deviceId === 0 || deviceId === '0') {
		if (Ext.getCmp(gridPanelId) && Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length > 0) {
			var sel = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data;
			calculateType = sel.calculateType;
			deviceId = sel.id;
			deviceName = sel.deviceName;
		}
	}

	if(Ext.getCmp(panelId)!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getItemRealTimeCurveData',
		success:function(response) {
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
            }
			
			var result =  Ext.JSON.decode(response.responseText);
			var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var data = result.list;
		    var totals=result.curveCount;
		    var legendName =result.curveItems;
		    
		    var colors=["#7cb5ec"];
		   
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 2) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    
		    $('#ItemRealtimeCurveContainer').append('<div id="ItemRealtimeCurveDiv_Id" style="width:100%;height:100%;"></div>');
            var divId = 'ItemRealtimeCurveDiv_Id';
		    
        	var xTitle='';
        	var yTitle=legendName[0];
        	var title = result.deviceName+":"+legendName[0].split("(")[0] + loginUserLanguageResource.trendCurve;
        	var subtitle='';
        	
		    var color=["#7cb5ec"];
		    
		    var maxValue=null;
	        var minValue=null;
	        var allPositive=true;
	        var allNegative=true;
	        
	        var yAxisOpposite=false;
	        
	        var series = [];
	        var seriesItem = {
	            name: legendName[0],
	            lineWidth: 2,
	            marker: { enabled: true },
	            dataGrouping: { enabled: false },
	            data: []
	        };
	        
	        for (var j = 0; j < data.length; j++) {
	            var timestamp = Date.parse(data[j].acqTime.replace(/-/g, '/'));
	            var value = parseFloat(data[j].data);
	            seriesItem.data.push([timestamp, value]);
	            
	            if(parseFloat(data[j].data)<0){
	            	allPositive=false;
	            }else if(parseFloat(data[j].data)>=0){
	            	allNegative=false;
	            }
	        }
	        series.push(seriesItem);
		    if(allNegative){
	        	maxValue=0;
	        }else if(allPositive){
	        	minValue=0;
	        }
		    var timeFormat='%H:%M';
		    initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle, xTitle, yTitle,color,false,true,false,timeFormat,maxValue,minValue,yAxisOpposite);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			calculateType:calculateType,
			itemName:itemName,
			itemCode:itemCode,
			itemType:itemType,
			itemResolutionMode:itemResolutionMode
        }
	});
}