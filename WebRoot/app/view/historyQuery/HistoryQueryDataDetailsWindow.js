var deviceHistoryQueryDataHandsontableHelper=null;
Ext.define("AP.view.historyQuery.HistoryQueryDataDetailsWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.historyQueryDataDetailsWindow',
    id: 'HistoryQueryDataDetailsWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.detailsData,
    border: false,
    hidden: false,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
//    closable: 'sides',
//    closeAction: 'destroy',
//    maximizable: true,
//    minimizable: true,
//    collapsible: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: '80%',
    height: '80%',
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	items:[{
        		region: 'center',
        		layout: 'fit',
        		tbar:[{
                    id: 'HistoryQueryDataDetailsWindowRecord_Id',
                    xtype: 'textfield',
                    value: -1,
                    hidden: true
                },{
                    id: 'HistoryQueryDataDetailsWindowDeviceName_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryQueryDataDetailsWindowDeviceId_Id',
                    xtype: 'textfield',
                    value: '0',
                    hidden: true
                },{
                    id: 'HistoryQueryDataDetailsWindowDeviceCalculateType_Id',
                    xtype: 'textfield',
                    value: '0',
                    hidden: true
                },{
                	xtype: 'label',
                	html: loginUserLanguageResource.viewCurveOrTableData,
                	style: {
                        marginLeft: '10px',
                        fontSize: '11px',
                        fontFamily: 'tahoma,arial,verdana,sans-serif',
                        color: '#333333'
                    }
                },'->',{
                    xtype: 'button',
                    text: loginUserLanguageResource.exportData,
                    iconCls: 'export',
                    hidden:false,
                    handler: function (v, o) {
                    	var recordId=Ext.getCmp("HistoryQueryDataDetailsWindowRecord_Id").getValue();
                		var deviceId=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceId_Id").getValue()
                		var deviceName=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceName_Id").getValue();
                		var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                		var calculateType=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceCalculateType_Id").getValue();
              			exportDeviceHistoryQueryDetailsData(recordId,deviceId,deviceName,deviceType,calculateType);
                    }
                }],
                id:'HistoryQueryDataDetailsPanel_Id',
            	html: '<div id="HistoryQueryDataDetailsDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(deviceHistoryQueryDataHandsontableHelper!=null&&deviceHistoryQueryDataHandsontableHelper.hot!=null&&deviceHistoryQueryDataHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		deviceHistoryQueryDataHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		var recordId=Ext.getCmp("HistoryQueryDataDetailsWindowRecord_Id").getValue();
                    		var deviceId=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceId_Id").getValue()
                    		var deviceName=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceName_Id").getValue();
                    		var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                    		var calculateType=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceCalculateType_Id").getValue();
                  			CreateDeviceHistoryQueryDataTable(recordId,deviceId,deviceName,deviceType,calculateType);
                    	}
                    }
                }
        	}],
        	listeners: {
        		beforeclose: function ( panel, eOpts) {
                	if(deviceHistoryQueryDataHandsontableHelper!=null){
    					if(deviceHistoryQueryDataHandsontableHelper.hot!=undefined){
    						deviceHistoryQueryDataHandsontableHelper.hot.destroy();
    					}
    					deviceHistoryQueryDataHandsontableHelper=null;
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
                    var panel = Ext.getCmp('HistoryQueryDataDetailsPanel_Id');
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
                    var panel = Ext.getCmp('HistoryQueryDataDetailsPanel_Id');
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

                    win._panel = win.down('#HistoryQueryDataDetailsPanel_Id');
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
                        var panel = Ext.getCmp('HistoryQueryDataDetailsPanel_Id');
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
                            var panel = Ext.getCmp('HistoryQueryDataDetailsPanel_Id');
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

function viewItemHistoryCurve(itemName, itemValue, cellInfo) {
    // 延迟显示，避开 Handsontable 的事件冒泡
    Ext.Function.defer(function() {
    	// 先关闭可能已存在的同 id 窗口（确保每次都是新窗口）
        var existingWin = Ext.getCmp('ItemHistoryCurveWindow_Id');
        if (existingWin) {
            existingWin.close();
        }
        
        // 创建新窗口
        var curveWin = Ext.create('AP.view.historyQuery.ItemHistoryCurveWindow');
        
        // 设置子组件值（注意：窗口可能还没渲染，使用 afterrender 或直接 setValue 如果组件已存在）
        curveWin.on('afterrender', function() {
            Ext.getCmp('HistoryCurveItemName_Id').setValue(itemName);
            Ext.getCmp('HistoryCurveItemCode_Id').setValue(cellInfo.column);
            Ext.getCmp('HistoryCurveItemType_Id').setValue(cellInfo.type);
            Ext.getCmp('HistoryCurveItemResolutionMode_Id').setValue(cellInfo.resolutionMode);
            
            Ext.getCmp('ItemHistoryCurveStartDate_Id').setValue(Ext.getCmp('HistoryQueryStartDate_Id').getValue());
            Ext.getCmp('ItemHistoryCurveStartTime_Hour_Id').setValue(Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue());
            Ext.getCmp('ItemHistoryCurveStartTime_Minute_Id').setValue(Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue());
            
            Ext.getCmp('ItemHistoryCurveEndDate_Id').setValue(Ext.getCmp('HistoryQueryEndDate_Id').getValue());
            Ext.getCmp('ItemHistoryCurveEndTime_Hour_Id').setValue(Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue());
            Ext.getCmp('ItemHistoryCurveEndTime_Minute_Id').setValue(Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue());
        }, { single: true });
        curveWin.show();
    }, 50);
}

function viewItemHistoryDataTable(itemName,itemValue,cellInfo){
	// 延迟显示，避开 Handsontable 的事件冒泡
    Ext.Function.defer(function() {
        var existingWin = Ext.getCmp('ItemHistoryDataWindow_Id');
        if (existingWin) {
            existingWin.close();
        }
        
        // 创建新窗口
        var dataWin = Ext.create('AP.view.historyQuery.ItemHistoryDataWindow');
        
        // 设置子组件值（注意：窗口可能还没渲染，使用 afterrender 或直接 setValue 如果组件已存在）
        dataWin.on('afterrender', function() {
            Ext.getCmp('HistoryDataItemName_Id').setValue(itemName);
            Ext.getCmp('HistoryDataItemCode_Id').setValue(cellInfo.column);
            Ext.getCmp('HistoryDataItemType_Id').setValue(cellInfo.type);
            Ext.getCmp('HistoryDataItemResolutionMode_Id').setValue(cellInfo.resolutionMode);
            if((cellInfo.type==0||cellInfo.type==5) && cellInfo.resolutionMode==0){
            	Ext.getCmp('HistoryDataItemBitIndex_Id').setValue(cellInfo.bitIndex);
            }else{
            	Ext.getCmp('HistoryDataItemBitIndex_Id').setValue('');
            }
            
            
            Ext.getCmp('ItemHistoryDataStartDate_Id').setValue(Ext.getCmp('HistoryQueryStartDate_Id').getValue());
            Ext.getCmp('ItemHistoryDataStartTime_Hour_Id').setValue(Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue());
            Ext.getCmp('ItemHistoryDataStartTime_Minute_Id').setValue(Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue());
            
            Ext.getCmp('ItemHistoryDataEndDate_Id').setValue(Ext.getCmp('HistoryQueryEndDate_Id').getValue());
            Ext.getCmp('ItemHistoryDataEndTime_Hour_Id').setValue(Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue());
            Ext.getCmp('ItemHistoryDataEndTime_Minute_Id').setValue(Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue());
            
            var gridPanel = Ext.getCmp("ItemHistoryQueryDataGridPanel_Id");
            if(isNotVal(gridPanel)){
            	gridPanel.getStore().loadPage(1);
            }else{
            	Ext.create('AP.store.historyQuery.ItemHistoryDataStore');
            }
        }, { single: true });
        dataWin.show();
    }, 50);
	
}

function viewItemHistoryData(row,col){
	if(deviceHistoryQueryDataHandsontableHelper!=null && deviceHistoryQueryDataHandsontableHelper.hot!=undefined && row>0){
		if(col%2==1){
			col=(col-1)/2;
		}else{
			col=col/2;
		}
		
		var cellInfo=null;
		for(var i=0;i<deviceHistoryQueryDataHandsontableHelper.CellInfo.length;i++){
			if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row==row && deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col==col){
				cellInfo=deviceHistoryQueryDataHandsontableHelper.CellInfo[i];
			}
		}
		if(cellInfo!=null){
			var itemName=deviceHistoryQueryDataHandsontableHelper.hot.getDataAtCell(row,col*2);
			var itemValue=deviceHistoryQueryDataHandsontableHelper.hot.getDataAtCell(row,col*2+1);
			var info=itemName+':'+itemValue;
			if(cellInfo.type==0){
				if(cellInfo.resolutionMode==2){
					info+=',采集数据量';
					if(cellInfo.columnDataType.toUpperCase()!='string'.toUpperCase() && isNumber(itemValue)){
						viewItemHistoryCurve(itemName,itemValue,cellInfo);
					}else{
						viewItemHistoryDataTable(itemName,itemValue,cellInfo);
					}
				}else if(cellInfo.resolutionMode==0){
					info+=',采集开关量';
					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
				}else if(cellInfo.resolutionMode==1){
					info+=',采集枚举量';
					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
				}
			}else if(cellInfo.type==1){
				info+=',计算项';
				if(isNumByCalculateItemCode(cellInfo.column)){
					viewItemHistoryCurve(itemName,itemValue,cellInfo);
				}else{
					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
				}
//				if(isNumber(itemValue)){
//					viewItemHistoryCurve(itemName,itemValue,cellInfo);
//				}else{
//					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
//				}
			}else if(cellInfo.type==3){
				info+=',录入项';
				if(isNumber(itemValue)){
					viewItemHistoryCurve(itemName,itemValue,cellInfo);
				}else{
					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
				}
			}else if(cellInfo.type==5){
				if(cellInfo.resolutionMode==2){
					info+=',协议拓展项数据量';
					if(isNumber(itemValue)){
						viewItemHistoryCurve(itemName,itemValue,cellInfo);
					}else{
						viewItemHistoryDataTable(itemName,itemValue,cellInfo);
					}
				}else if(cellInfo.resolutionMode==0){
					info+=',协议拓展项开关量';
					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
				}else if(cellInfo.resolutionMode==1){
					info+=',协议拓展项枚举量';
					viewItemHistoryDataTable(itemName,itemValue,cellInfo);
				}else if(cellInfo.resolutionMode==7){
					info+=',协议拓展项数值运算项';
					if(isNumber(itemValue)){
						viewItemHistoryCurve(itemName,itemValue,cellInfo);
					}else{
						viewItemHistoryDataTable(itemName,itemValue,cellInfo);
					}
				}	
			}
			console.log(info);
		}
	}
	
}

function CreateDeviceHistoryQueryDataTable(recordId,deviceId,deviceName,deviceType,calculateType){
	if(deviceHistoryQueryDataHandsontableHelper!=null){
		if(deviceHistoryQueryDataHandsontableHelper.hot!=undefined){
			deviceHistoryQueryDataHandsontableHelper.hot.destroy();
		}
		deviceHistoryQueryDataHandsontableHelper=null;
	}
	
	if(Ext.getCmp("HistoryQueryDataDetailsPanel_Id")!=undefined){
		Ext.getCmp("HistoryQueryDataDetailsPanel_Id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
	var startTime_Second=0;

    var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
    var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
	var endTime_Second=0;
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getDeviceHistoryDetailsData',
		success:function(response) {
			if(Ext.getCmp("HistoryQueryDataDetailsPanel_Id")!=undefined){
				Ext.getCmp("HistoryQueryDataDetailsPanel_Id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(deviceHistoryQueryDataHandsontableHelper==null || deviceHistoryQueryDataHandsontableHelper.hot==undefined){
				deviceHistoryQueryDataHandsontableHelper = DeviceHistoryQueryDataHandsontableHelper.createNew("HistoryQueryDataDetailsDiv_Id");
				var colHeaders="['"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[" 
						+"{data:'name1'}," 
						+"{data:'value1'}," 
						+"{data:'name2'},"
						+"{data:'value2'}," 
						+"{data:'name3'}," 
						+"{data:'value3'}"
						+"]";
				deviceHistoryQueryDataHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceHistoryQueryDataHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceHistoryQueryDataHandsontableHelper.CellInfo=result.CellInfo;
				if(result.totalRoot.length==0){
					deviceHistoryQueryDataHandsontableHelper.sourceData=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					deviceHistoryQueryDataHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					deviceHistoryQueryDataHandsontableHelper.sourceData=result.totalRoot;
					deviceHistoryQueryDataHandsontableHelper.createTable(result.totalRoot);
				}
				
				deviceHistoryQueryDataHandsontableHelper.hot.addHook('afterOnCellMouseDown', function(event, coords, td) {
				    // 检查是否为双击
				    if (deviceHistoryQueryDataHandsontableHelper.doubleClickTimer != null 
				    		&& deviceHistoryQueryDataHandsontableHelper.lastClickRow === coords.row 
				    		&& deviceHistoryQueryDataHandsontableHelper.lastClickCol === coords.col) {
				        clearTimeout(deviceHistoryQueryDataHandsontableHelper.doubleClickTimer);
				        deviceHistoryQueryDataHandsontableHelper.doubleClickTimer = null;
				        viewItemHistoryData(coords.row,coords.col);
				        // TODO: 在这里调用打开弹窗的函数
				        deviceHistoryQueryDataHandsontableHelper.lastClickRow = -1;
				        deviceHistoryQueryDataHandsontableHelper.lastClickCol = -1;
				    } else {
				        // 如果是第一次点击，设置定时器
				        deviceHistoryQueryDataHandsontableHelper.lastClickRow = coords.row;
				        deviceHistoryQueryDataHandsontableHelper.lastClickCol = coords.col;
				        if (deviceHistoryQueryDataHandsontableHelper.doubleClickTimer) {
				            clearTimeout(deviceHistoryQueryDataHandsontableHelper.doubleClickTimer);
				        }
				        deviceHistoryQueryDataHandsontableHelper.doubleClickTimer = setTimeout(() => {
				            deviceHistoryQueryDataHandsontableHelper.doubleClickTimer = null;
				        }, 250);
				    }
				});
			}else{
				deviceHistoryQueryDataHandsontableHelper.CellInfo=result.CellInfo;
				deviceHistoryQueryDataHandsontableHelper.sourceData=result.totalRoot;
				deviceHistoryQueryDataHandsontableHelper.hot.loadData(result.totalRoot);
			}
			//添加单元格属性
			for(var i=0;i<deviceHistoryQueryDataHandsontableHelper.CellInfo.length;i++){
				var row=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
				var col=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col;
				var column=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].column;
				var columnDataType=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].columnDataType;
				deviceHistoryQueryDataHandsontableHelper.hot.setCellMeta(row,col,'columnDataType',columnDataType);
			}
			
			deviceHistoryQueryDataHandsontableHelper.doubleClickTimer = null;
	        deviceHistoryQueryDataHandsontableHelper.lastClickRow = -1;
	        deviceHistoryQueryDataHandsontableHelper.lastClickCol = -1;
		},
		failure:function(){
			if(Ext.getCmp("HistoryQueryDataDetailsPanel_Id")!=undefined){
				Ext.getCmp("HistoryQueryDataDetailsPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			recordId: recordId,
			deviceType: deviceType,
			deviceId: deviceId,
			deviceName: deviceName,
			calculateType: calculateType,
			startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
        }
	});
};

var DeviceHistoryQueryDataHandsontableHelper = {
		createNew: function (divid) {
	        var deviceHistoryQueryDataHandsontableHelper = {};
	        deviceHistoryQueryDataHandsontableHelper.divid = divid;
	        deviceHistoryQueryDataHandsontableHelper.validresult=true;//数据校验
	        deviceHistoryQueryDataHandsontableHelper.colHeaders=[];
	        deviceHistoryQueryDataHandsontableHelper.columns=[];
	        deviceHistoryQueryDataHandsontableHelper.CellInfo=[];
	        
	        deviceHistoryQueryDataHandsontableHelper.sourceData=[];
	        
	        deviceHistoryQueryDataHandsontableHelper.doubleClickTimer = null;
	        deviceHistoryQueryDataHandsontableHelper.lastClickRow = -1;
	        deviceHistoryQueryDataHandsontableHelper.lastClickCol = -1;
	        
	        deviceHistoryQueryDataHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
//		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
            	
//	            if(row>0){
//	            	td.style.textAlign='left';//内容左对齐
//	            }
	            
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
	            if (row ==0) {
	            	Handsontable.renderers.TextRenderer.apply(this, arguments);
//		        	td.style.fontWeight = 'bold';
			        td.style.fontSize = '20px';
//			        td.style.fontFamily = 'SimSun';
			        td.style.height = '40px';
	            }
	            if (row%2==1&&row>0) {
	            	td.style.backgroundColor = '#f5f5f5';
                }
	            if (col%2==0) {
//	            	td.style.fontWeight = 'bold';
                }else{
//                	td.style.fontFamily = 'SimHei';
                }
	            for(var i=0;i<deviceHistoryQueryDataHandsontableHelper.CellInfo.length;i++){
	            	if( isNotVal(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyColor) ){
                		var row2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col*2;
        				if(row==row2 && col==col2 ){
        					td.style.color='#'+deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyColor;
        				}
                	}
                	
                	if( isNotVal(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyBgColor) ){
                		var row2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col*2;
        				if(row==row2 && col==col2 ){
        					td.style.backgroundColor = '#' + deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyBgColor;
        				}
                	}
	            	
	            	
	            	if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel>=0){
                		var row2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col*2+1;
        				if(row==row2 && col==col2 ){
        					if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel>0){
        						td.style.fontWeight = 'bold';
//       			             	td.style.fontFamily = 'SimHei';
        					}
   			             	if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==0){
			             		if(AlarmShowStyle.Data.Normal.Opacity!=0){
			             			td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.Normal.BackgroundColor,AlarmShowStyle.Data.Normal.Opacity);
			             		}
			             		td.style.color='#'+AlarmShowStyle.Data.Normal.Color;
			             	}else if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==100){
        						if(AlarmShowStyle.Data.FirstLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor,AlarmShowStyle.Data.FirstLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.FirstLevel.Color;
        					}else if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==200){
        						if(AlarmShowStyle.Data.SecondLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor,AlarmShowStyle.Data.SecondLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.SecondLevel.Color;
        					}else if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==300){
        						if(AlarmShowStyle.Data.ThirdLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor,AlarmShowStyle.Data.ThirdLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.ThirdLevel.Color;
        					}
        				}
                	}
    			}
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceHistoryQueryDataHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceHistoryQueryDataHandsontableHelper.divid);
	        	deviceHistoryQueryDataHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		theme: 'ht-theme-classic',
	        		data: data,
	        		colWidths: [30,20,30,20,30,20],
	                columns:deviceHistoryQueryDataHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: false,
	                rowHeights: [40],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: [{
                        "row": 0,
                        "col": 0,
                        "rowspan": 1,
                        "colspan": 6
                    }],
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = deviceHistoryQueryDataHandsontableHelper.addCellStyle;
	                    
	                    cellProperties.editor = false;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && deviceHistoryQueryDataHandsontableHelper!=null&&deviceHistoryQueryDataHandsontableHelper.hot!=''&&deviceHistoryQueryDataHandsontableHelper.hot!=undefined && deviceHistoryQueryDataHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var record=deviceHistoryQueryDataHandsontableHelper.sourceData[coords.row];
	                		var rawValue='';
	                		if(coords.col==0){
	                			rawValue=record.name1;
	                		}else if(coords.col==1){
	                			rawValue=record.value1;
	                		}else if(coords.col==2){
	                			rawValue=record.name2;
	                		}else if(coords.col==3){
	                			rawValue=record.value2;
	                		}else if(coords.col==4){
	                			rawValue=record.name3;
	                		}else if(coords.col==5){
	                			rawValue=record.value3;
	                		}
                			if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return deviceHistoryQueryDataHandsontableHelper;
	    }
};

function exportDeviceHistoryQueryDetailsData(recordId,deviceId,deviceName,deviceType,calculateType) {
	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
	var startTime_Second=0;

    var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
    var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
	var endTime_Second=0;
	
	var timestamp=new Date().getTime();
	var key='exportDeviceHistoryQueryDetailsData_'+deviceId+'_'+timestamp;
	var maskPanelId='HistoryQueryDataDetailsPanel_Id';
	var url = context + '/historyQueryController/exportDeviceHistoryQueryDetailsData';
    var param = "&recordId=" + recordId
    + "&deviceId=" + deviceId
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&deviceType=" + deviceType
    + '&calculateType='+calculateType
    + "&startDate=" + getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second)
    + "&endDate=" + getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
};
