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
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 600,
    minWidth: 600,
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
                margin: '0 0 0 0',
                id:'DeviceControlItemName_Id',
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
                text: '确定',
                iconCls: 'edit',
                handler: function (v, o) {
            		var all_loading = new Ext.LoadMask({
                        msg: '命令发送中，请稍后...',
                        target: Ext.getCmp('DeviceControlCheckPassWindow_Id')
                    });
                	all_loading.show();
                	
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
                            		Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                                    Ext.MessageBox.show({
                                        title: cosog.string.ts,
                                        msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
                                        icon: Ext.MessageBox.INFO,
                                        buttons: Ext.Msg.OK,
                                        fn: function () {
                                            window.location.href = context + "/login/toLogin";
                                        }
                                    });
                                } else if (result.flag == true && result.error == false) {
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>" + result.msg + "</font>");
                                }  else if (result.flag == true && result.error == true) {
                                	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>" + result.msg + "</font>");
                                } 
                            },
                            failure: function () {
                            	all_loading.hide();
                            	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
                            }
                        });
                	}else{
                		all_loading.hide();
                		Ext.Msg.alert(cosog.string.ts, "<font color=red>数据格式有误，请检查！</font>");
                	}
                	
            	}
            }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                }
            }],
        	items:[{
        		region: 'center',
        		layout: 'fit',
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
                  			CreateDeviceControlValueTable();
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
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});


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
				var colHeaders="['序号','数值']";
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
				
				
				columns+="]";
				deviceControlValueHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceControlValueHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceControlValueHandsontableHelper.createTable(result.totalRoot);
			}else{
				deviceControlValueHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
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
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        deviceControlValueHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceControlValueHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceControlValueHandsontableHelper.divid);
	        	deviceControlValueHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:deviceControlValueHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: deviceControlValueHandsontableHelper.colHeaders,
	                colWidths: [1,8],
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
	                    if(visualColIndex==0){
	                    	cellProperties.renderer = deviceControlValueHandsontableHelper.addBoldBg;
	                    	cellProperties.readOnly = true;
	                    }
	                    return cellProperties;
	                }
	        	});
	        }
	        return deviceControlValueHandsontableHelper;
	    }
};