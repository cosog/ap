var deviceControlValueHandsontableHelper=null;
Ext.define("AP.view.realTimeMonitoring.DeviceControlCheckPassWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.deviceControlCheckPassWindow',
    id: 'DeviceControlCheckPassWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.deviceControl,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: 800,
    minWidth: 800,
    height: 410,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    padding:0,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout:'border',
            tbar:[{
                xtype: 'label',
                margin: '0 0 0 5',
                id:'DeviceControlShowInfo_Id',
                html: ''
            },{
                id: 'DeviceControlDeviceId_Id',//选择的设备Id
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlDeviceName_Id',//选择的设备
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlItemName_Id',//选择的项名称
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlItemUnit_Id',//选择的项单位
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlDeviceType_Id',//选择的设备类型
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'DeviceControlShowType_Id',//显示类型 0-不显示 1-输入框 2-下拉框
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'DeviceControlStoreDataType_Id',//存储数据类型
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlQuantity_Id',//存储数据类型
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'DeviceControlType_Id',//控制项
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlItemMeaning_Id',//项含义
                xtype: 'textfield',
                value: '',
                hidden: true
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.uplink,
                iconCls: 'uplink',
                id:'DeviceControlDataUplinkBtn_Id',
                handler: function (v, o) {
                	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
            		if(resolutionMode!=1){
            			deviceDataUplinkFun();
            		}
                }
            },'-',{
                xtype: 'button',
                text: loginUserLanguageResource.downlink,
                iconCls: 'downlink',
                id:'DeviceControlConfirmBtn_Id',
                handler: function (v, o) {
                	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
            		if(resolutionMode!=1){
            			deviceControlFun();
            		}
                }
            }, {
                text: loginUserLanguageResource.close,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                }
            }],
        	items:[{
        		region: 'center',
        		layout: 'fit',
        		id:'DeviceControlValueTablePanel_Id',
        		html: '<div id="DeviceControlValueTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(deviceControlValueHandsontableHelper!=null&&deviceControlValueHandsontableHelper.hot!=null&&deviceControlValueHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		deviceControlValueHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
                    		if(resolutionMode==0){
                    			Ext.create('AP.store.realTimeMonitoring.DeviceControlSwitchingValueStore');
                    		}if(resolutionMode==1){
                    			Ext.create('AP.store.realTimeMonitoring.DeviceControlEnumValueStore');
                    		}else{
                    			CreateDeviceControlValueTable();
                    		}
                    	}
                    }
        		}
        	}],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(deviceControlValueHandsontableHelper!=null){
    					if(deviceControlValueHandsontableHelper.hot!=undefined){
    						deviceControlValueHandsontableHelper.hot.destroy();
    					}
    					deviceControlValueHandsontableHelper=null;
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
                    var panel = Ext.getCmp('DeviceControlValueTablePanel_Id');
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
                    var panel = Ext.getCmp('DeviceControlValueTablePanel_Id');
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

                    win._panel = win.down('#DeviceControlValueTablePanel_Id');
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
//                        win.setTitle(win.originalTitle + ' (已最小化)');
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
                        var panel = Ext.getCmp('DeviceControlValueTablePanel_Id');
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
                            var panel = Ext.getCmp('DeviceControlValueTablePanel_Id');
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

function createDeviceControlButtonRenderer(buttonText, clickHandler, bgColor, hoverColor = null) {
    if (!hoverColor) {
        // 简单提亮颜色（或者手动传参）
        hoverColor = bgColor === '#409eff' ? '#66b1ff' :
                     bgColor === '#67c23a' ? '#85ce61' :
                     bgColor === '#e6a23c' ? '#ebb563' : '#909399';
    }
    return function(instance, td, row, col, prop, value, cellProperties) {
        td.innerHTML = '';
        const container = document.createElement('div');
        container.style.display = 'flex';
        container.style.justifyContent = 'center';
        container.style.gap = '8px';
        
        const btn = document.createElement('button');
        btn.textContent = buttonText;
        
        // 公共样式
        btn.style.padding = '2px 14px';
        btn.style.fontSize = '12px';
        btn.style.fontWeight = '500';
        btn.style.border = 'none';
        btn.style.borderRadius = '20px';
        btn.style.cursor = 'pointer';
        btn.style.backgroundColor = bgColor;
        btn.style.color = 'white';
        btn.style.transition = 'all 0.2s';
        btn.style.boxShadow = '0 1px 2px rgba(0,0,0,0.1)';
        
        // 悬停效果
        btn.addEventListener('mouseenter', () => {
            btn.style.backgroundColor = hoverColor;
            btn.style.transform = 'translateY(-1px)';
        });
        btn.addEventListener('mouseleave', () => {
            btn.style.backgroundColor = bgColor;
            btn.style.transform = 'translateY(0)';
        });
        
        btn.onclick = (e) => {
            e.stopPropagation();
            clickHandler(instance, td, row, col, prop, value, cellProperties);
        };
        
        container.appendChild(btn);
        td.appendChild(container);
        return td;
    };
}

function deviceControlFunRenderer(){
	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
	if(resolutionMode!=1){
		deviceControlFun();
	}
}

function deviceDataUplinkFunRenderer(){
	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
	if(resolutionMode!=1){
		deviceDataUplinkFun();
	}
}

function CreateDeviceControlValueTable(){
	var deviceId= Ext.getCmp('DeviceControlDeviceId_Id').getValue();
	var deviceName= Ext.getCmp('DeviceControlDeviceName_Id').getValue();
	var deviceType= Ext.getCmp('DeviceControlDeviceType_Id').getValue();
	var controlType= Ext.getCmp('DeviceControlType_Id').getValue();
	var storeDataType= Ext.getCmp('DeviceControlStoreDataType_Id').getValue();
	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
	var quantity= Ext.getCmp('DeviceControlQuantity_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getDeviceControlValueList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(deviceControlValueHandsontableHelper==null || deviceControlValueHandsontableHelper.hot==undefined){
				deviceControlValueHandsontableHelper = DeviceControlValueHandsontableHelper.createNew("DeviceControlValueTableDiv_Id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.variable+"','"+loginUserLanguageResource.uplinkStatus+"']";
				var columns="[" 
						+"{data:'index'}," ;
				
				if(resolutionMode==1 && quantity==1){
					var itemMeaning=Ext.getCmp('DeviceControlItemMeaning_Id').getValue();
					itemMeaning=Ext.JSON.decode(itemMeaning);
					if(isNotVal(itemMeaning) && itemMeaning.length>0){
						var itemMeaningStr="";
						for(var i=0;i<itemMeaning.length;i++){
							itemMeaningStr+="'"+itemMeaning[i][1]+"'";
							if(i<itemMeaning.length-1){
								itemMeaningStr+=',';
							}
						}
						
						columns+="{data:'value',type:'dropdown',strict:true,allowInvalid:false,source:["+itemMeaningStr+"]}," 
					}else{
						if(storeDataType.toUpperCase()=='BCD' || storeDataType.toUpperCase()=='STRING'){
							columns+="{data:'value'}";
						}else{
							columns+="{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,deviceControlValueHandsontableHelper);}}" 
						}
					}
				}else{
					if(storeDataType.toUpperCase()=='BCD' || storeDataType.toUpperCase()=='STRING'){
						columns+="{data:'value'}";
					}else{
						columns+="{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,deviceControlValueHandsontableHelper);}}" 
					}
				}
				
				columns+=",{data:'uplinkStatus'}";
				columns+="]";
				
				var colHeaderList=Ext.JSON.decode(colHeaders);
				var columnList= Ext.JSON.decode(columns);
				
				if(result.totalRoot.length==1){//当只有一行时
					Ext.getCmp("DeviceControlConfirmBtn_Id").hide();
					Ext.getCmp("DeviceControlDataUplinkBtn_Id").hide();
					
					colHeaderList.push(loginUserLanguageResource.uplink);
					colHeaderList.push(loginUserLanguageResource.downlink);
					
					
					var uplinkColumn={
	                        data: 'uplink',
	                        renderer: createDeviceControlButtonRenderer(
	                        		loginUserLanguageResource.uplink,
	                                (instance, td, row, col, prop, value, cellProperties) => 
	                                deviceDataUplinkFunRenderer(),
	                                '#409eff'  // 蓝色
	                            ),
	                        readOnly: true
	                    };
					var downlinkColumn={
	                        data: 'downlink',
	                        renderer: createDeviceControlButtonRenderer(
	                        		loginUserLanguageResource.downlink,
	                                (instance, td, row, col, prop, value, cellProperties) => 
	                                deviceControlFunRenderer(),
	                                '#67c23a'  // 绿色
	                            ),
	                        readOnly: true
	                    };
					
					columnList.push(uplinkColumn);
					columnList.push(downlinkColumn);
					deviceControlValueHandsontableHelper.colWidths=[15,60,60,40,40]
				}else{
					deviceControlValueHandsontableHelper.colWidths=[15,80,80]
				}
				
				deviceControlValueHandsontableHelper.colHeaders=colHeaderList;
				deviceControlValueHandsontableHelper.columns=columnList;
				deviceControlValueHandsontableHelper.createTable(result.totalRoot);
			}else{
				deviceControlValueHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			deviceId: deviceId,
        	deviceName: deviceName,
        	deviceType: deviceType,
            controlType: controlType
        }
	});
};

var DeviceControlValueHandsontableHelper = {
		createNew: function (divid) {
	        var deviceControlValueHandsontableHelper = {};
	        deviceControlValueHandsontableHelper.divid = divid;
	        deviceControlValueHandsontableHelper.validresult=true;//数据校验
	        deviceControlValueHandsontableHelper.colHeaders=[];
	        deviceControlValueHandsontableHelper.columns=[];
	        deviceControlValueHandsontableHelper.colWidths=[];
	        
	        deviceControlValueHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        deviceControlValueHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        deviceControlValueHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
//		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        deviceControlValueHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceControlValueHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceControlValueHandsontableHelper.divid);
	        	deviceControlValueHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		theme: 'ht-theme-classic',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [2],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:deviceControlValueHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: deviceControlValueHandsontableHelper.colHeaders,
	                colWidths: deviceControlValueHandsontableHelper.colWidths,
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if(prop.toUpperCase()=='index'.toUpperCase() || prop.toUpperCase()=='uplinkStatus'.toUpperCase()){
	                    	cellProperties.renderer = deviceControlValueHandsontableHelper.addBoldBg;
	                    	cellProperties.editor = false;
	                    }
	                    return cellProperties;
	                }
	        	});
	        }
	        return deviceControlValueHandsontableHelper;
	    }
};



function renderEnumValueControlBtn(btn){
	var record = btn.up().getWidgetRecord();
    var text = record.data.meaning;
    
    var btnWidth=btn.width;
    var textLength=getLabelWidth(text,loginUserLanguage);
    if(textLength>btnWidth){
    	btn.setWidth(textLength);
    }
    
    btn.setText(text);
	btn.setTooltip(text);
}

function enumValueControlBtnHandler(btn){
	var record = btn.up().getWidgetRecord();
	var value = record.data.value;


	var all_loading = new Ext.LoadMask({
        msg: loginUserLanguageResource.commandSending+'...',
        target: Ext.getCmp('DeviceControlCheckPassWindow_Id')
    });
	all_loading.show();
	
	
	var controlValue=value;
	var storeDataType= Ext.getCmp('DeviceControlStoreDataType_Id').getValue();
	var quantity= Ext.getCmp('DeviceControlQuantity_Id').getValue();
	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
	

	Ext.Ajax.request({
        url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        method: "POST",
        params: {
        	deviceId: Ext.getCmp('DeviceControlDeviceId_Id').getValue(),
        	deviceName: Ext.getCmp('DeviceControlDeviceName_Id').getValue(),
        	deviceType: Ext.getCmp('DeviceControlDeviceType_Id').getValue(),
            controlType: Ext.getCmp('DeviceControlType_Id').getValue(),
            controlValue: controlValue,
            storeDataType: storeDataType,
            quantity: quantity
        },
        success: function (response, action) {
        	all_loading.hide();
        	var result =  Ext.JSON.decode(response.responseText);
        	
        	if (result.flag == false) {
//        		Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                Ext.MessageBox.show({
                    title: loginUserLanguageResource.tip,
                    msg: "<font color=red>" + loginUserLanguageResource.sessionExpired + "。</font>",
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OK,
                    fn: function () {
                        window.location.href = context + "/login";
                    }
                });
            } else if (result.flag == true && result.error == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
            }  else if (result.flag == true && result.error == true) {
//            	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
            } 
        },
        failure: function () {
        	all_loading.hide();
//        	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
        }
    });
}

function renderSwitchingValueControlBtn(btn){
	var record = btn.up().getWidgetRecord();
    var text = record.data.status;
    
    var btnWidth=btn.width;
    var textLength=getLabelWidth(text,loginUserLanguage);
    if(textLength>btnWidth){
    	btn.setWidth(textLength);
    }
    
    btn.setText(text);
	btn.setTooltip(text);
}

function switchingValueControlBtnHandler(btn){
	var record = btn.up().getWidgetRecord();
	var value = record.data.value;
	var bitIndex = record.data.bitIndex;

	var all_loading = new Ext.LoadMask({
        msg: loginUserLanguageResource.commandSending+'...',
        target: Ext.getCmp('DeviceControlCheckPassWindow_Id')
    });
	all_loading.show();
	
	
	var controlValue=value;
	var storeDataType= Ext.getCmp('DeviceControlStoreDataType_Id').getValue();
	var quantity= Ext.getCmp('DeviceControlQuantity_Id').getValue();
	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
	
	Ext.Ajax.request({
        url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        method: "POST",
        params: {
        	deviceId: Ext.getCmp('DeviceControlDeviceId_Id').getValue(),
        	deviceName: Ext.getCmp('DeviceControlDeviceName_Id').getValue(),
        	deviceType: Ext.getCmp('DeviceControlDeviceType_Id').getValue(),
            controlType: Ext.getCmp('DeviceControlType_Id').getValue(),
            bitIndex: bitIndex,
            controlValue: controlValue,
            storeDataType: storeDataType,
            quantity: quantity
        },
        success: function (response, action) {
        	all_loading.hide();
        	var result =  Ext.JSON.decode(response.responseText);
        	
        	if (result.flag == false) {
//        		Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                Ext.MessageBox.show({
                    title: loginUserLanguageResource.tip,
                    msg: "<font color=red>" + loginUserLanguageResource.sessionExpired + "。</font>",
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OK,
                    fn: function () {
                        window.location.href = context + "/login";
                    }
                });
            } else if (result.flag == true && result.error == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
            }  else if (result.flag == true && result.error == true) {
//            	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
            } 
        },
        failure: function () {
        	all_loading.hide();
//        	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
        }
    });
}

function deviceControlFun(){
	var all_loading = new Ext.LoadMask({
        msg: loginUserLanguageResource.commandSending+'...',
        target: Ext.getCmp('DeviceControlCheckPassWindow_Id')
    });
	
	
	var isValid=true;
	var controlValue='';
	var storeDataType= Ext.getCmp('DeviceControlStoreDataType_Id').getValue();
	var quantity= Ext.getCmp('DeviceControlQuantity_Id').getValue();
	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
	if(deviceControlValueHandsontableHelper!=null && deviceControlValueHandsontableHelper.hot!=null){
		var controlValueData=deviceControlValueHandsontableHelper.hot.getData();
		if(resolutionMode==1 && quantity==1){
			var itemMeaning=Ext.getCmp('DeviceControlItemMeaning_Id').getValue();
			itemMeaning=Ext.JSON.decode(itemMeaning);
			var controlValue="";
			
			if(isNotVal(itemMeaning) && itemMeaning.length>0){
				for(var i=0;i<itemMeaning.length;i++){
					if(controlValueData[0][1]==itemMeaning[i][1]){
						controlValue=itemMeaning[i][0];
						break;
					}
				}
			}
			if(!isNumber(controlValue)){
				isValid=false;
			}
		}else{
			for(var i=0;i<controlValueData.length;i++){
    			
    			if(isNotVal(controlValueData[i][1])){
    				controlValue+=controlValueData[i][1];
    			}else{
    				controlValue+=" ";
    			}
    			
    			if(i<controlValueData.length-1){
    				controlValue+=',';
    			}
    			if( !(storeDataType.toUpperCase()=='BCD' || storeDataType.toUpperCase()=='STRING') ){
    				if( isNotVal(controlValueData[i][1]) && (!isNumber(controlValueData[i][1])) ){
    					isValid=false;
    				}
    			}
    		}
		}
	}else{
		isValid=false;
	}
	if(isValid){
		var deviceName=Ext.getCmp("DeviceControlDeviceName_Id").getValue();
		var itemName=Ext.getCmp("DeviceControlItemName_Id").getValue();
		var itemUnit=Ext.getCmp("DeviceControlItemUnit_Id").getValue();
		
		var tipInfo=loginUserLanguageResource.deviceName+":<font color=red>"+deviceName+"</font>";
    	tipInfo+="</br>"+(itemName+(isNotVal(itemUnit)?"("+itemUnit+")":""))+":<font color=red>"+controlValue+"</font>";
    	tipInfo+="</br>"+loginUserLanguageResource.confirmOperation;
    	
    	Ext.Msg.confirm(loginUserLanguageResource.tip, tipInfo, function (btn) {
    	    if (btn == "yes") {
    	    	all_loading.show();
    	    	Ext.Ajax.request({
    	            url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
    	            method: "POST",
    	            params: {
    	            	deviceId: Ext.getCmp('DeviceControlDeviceId_Id').getValue(),
    	            	deviceName: Ext.getCmp('DeviceControlDeviceName_Id').getValue(),
    	            	deviceType: Ext.getCmp('DeviceControlDeviceType_Id').getValue(),
    	                controlType: Ext.getCmp('DeviceControlType_Id').getValue(),
    	                controlValue: controlValue,
    	                storeDataType: storeDataType,
    	                quantity: quantity
    	            },
    	            success: function (response, action) {
    	            	all_loading.hide();
    	            	var result =  Ext.JSON.decode(response.responseText);
    	            	
    	            	if (result.flag == false) {
//    	            		Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
    	                    Ext.MessageBox.show({
    	                        title: loginUserLanguageResource.tip,
    	                        msg: "<font color=red>" + loginUserLanguageResource.sessionExpired + "。</font>",
    	                        icon: Ext.MessageBox.INFO,
    	                        buttons: Ext.Msg.OK,
    	                        fn: function () {
    	                            window.location.href = context + "/login";
    	                        }
    	                    });
    	                } else if (result.flag == true && result.error == false) {
    	                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
    	                }  else if (result.flag == true && result.error == true) {
//    	                	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
    	                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
    	                } 
    	            },
    	            failure: function () {
    	            	all_loading.hide();
//    	            	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
    	                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
    	            }
    	        });
    	    }
    	});
	}else{
		all_loading.hide();
		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.dataFormattingError+"</font>");
	}
}

function deviceDataUplinkFun(){
	var all_loading = new Ext.LoadMask({
        msg: loginUserLanguageResource.commandSending+'...',
        target: Ext.getCmp('DeviceControlCheckPassWindow_Id')
    });
	

	all_loading.show();
	Ext.Ajax.request({
        url: context + '/wellInformationManagerController/deviceDataUplink',
        method: "POST",
        params: {
        	deviceId: Ext.getCmp('DeviceControlDeviceId_Id').getValue(),
        	deviceName: Ext.getCmp('DeviceControlDeviceName_Id').getValue(),
            controlType: Ext.getCmp('DeviceControlType_Id').getValue()
        },
        success: function (response, action) {
        	all_loading.hide();
        	var result =  Ext.JSON.decode(response.responseText);
        	
        	if (result.flag == false) {
//        		Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                Ext.MessageBox.show({
                    title: loginUserLanguageResource.tip,
                    msg: "<font color=red>" + loginUserLanguageResource.sessionExpired + "。</font>",
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OK,
                    fn: function () {
                        window.location.href = context + "/login";
                    }
                });
            } else if (result.flag == true && result.error == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
            }  else if (result.flag == true && result.error == true) {
            	const plugin = deviceControlValueHandsontableHelper.hot.getPlugin('hiddenColumns');
            	plugin.showColumns([2]);
            	deviceControlValueHandsontableHelper.hot.render();
            	
            	var uplinkStatusData=deviceControlValueHandsontableHelper.hot.getDataAtProp('uplinkStatus');
            	
            	var uplinkData=result.data.split(",");;
            	
            	for(var i=0;i<uplinkData.length;i++){
            		if(uplinkStatusData.length>i){
            			deviceControlValueHandsontableHelper.hot.setDataAtRowProp(i,'uplinkStatus',uplinkData[i]);
            		}
            	}
            } 
        },
        failure: function () {
        	all_loading.hide();
//        	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
        }
    });


}