var a9RawDataWebsocket = null;
Ext.define('AP.view.kafkaConfig.A9RawDataInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.a9RawDataInfoView',
    layout: "fit",
    id:'a9RawDataInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var A9RawDataDeviceListStore=Ext.create('AP.store.kafkaConfig.A9RowDataListStore');
    	var deviceComboBoxStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/kafkaConfigController/loadDeviceComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var deviceName = Ext.getCmp('A9RawDataDeviceCom_Id').getValue();
                    var new_params = {
                    		deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var deviceComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '设备ID',
            id: "A9RawDataDeviceCom_Id",
            store: deviceComboBoxStore,
            labelWidth: 45,
            width: 180,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize: comboxPagingStatus,
            minChars: 0,
            multiSelect: false,
            listeners: {
                expand: function (sm, selections) {
                	deviceComboBox.getStore().loadPage(1);
                },
                select: function (combo, record, index) {
                	if(combo.value==""){
                		Ext.getCmp("a9RawDataInfoPanelId").setTitle("实时数据");
                		Ext.getCmp("A9RawDataAllBtn_Id").hide();
                  	  	Ext.getCmp("A9RawDataHisBtn_Id").show();
                  	  	Ext.getCmp("A9RawDataStartDate_Id").hide();
                  	  	Ext.getCmp("A9RawDataEndDate_Id").hide();
                	}else{
                		Ext.getCmp("a9RawDataInfoPanelId").setTitle("设备历史");
            			Ext.getCmp("A9RawDataAllBtn_Id").show();
                		Ext.getCmp("A9RawDataHisBtn_Id").hide();
                		Ext.getCmp("A9RawDataStartDate_Id").show();
                		Ext.getCmp("A9RawDataEndDate_Id").show();
                	}
                	Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
                }
            }
        });
    	Ext.apply(me, {
    		items: [{
//                layout: "border",
                border: false,
                id:'a9RawDataInfoPanelId',
                title: '实时数据',
                height: 1500,
                autoScroll:false,
                scrollable: true,
        		tbar: [deviceComboBox,'-',{
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '',
                    labelWidth: 0,
                    width: 90,
                    format: 'Y-m-d ',
                    id: 'A9RawDataStartDate_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '至',
                    labelWidth: 15,
                    width: 105,
                    format: 'Y-m-d ',
                    id: 'A9RawDataEndDate_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                },'-', {
                    xtype: 'button',
                    text: cosog.string.exportExcel,
                    pressed: true,
                    hidden:false,
                    handler: function (v, o) {
                    	exportA9RawDataExcel();
                    }
                },'-',{
                    xtype: 'button',
                    iconCls: 'note-refresh',
                    text: cosog.string.refresh,
                    id:'A9RawDataFreshBtn_Id',
                    pressed: true,
                    hidden:false,
                    handler: function (v, o) {
                    	Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
                    }
                
        		}, '->', {
                    xtype: 'button',
                    text:'设备历史',
                    tooltip:'点击按钮或者双击表格，查看设备历史数据',
                    id:'A9RawDataHisBtn_Id',
                    pressed: true,
                    hidden: false,
                    handler: function (v, o) {
//                		if(Ext.getCmp("A9RawDataGridPanel_Id").getSelectionModel().getSelection().length>0){
                			Ext.getCmp("a9RawDataInfoPanelId").setTitle("设备历史");
                			Ext.getCmp("A9RawDataAllBtn_Id").show();
                    		Ext.getCmp("A9RawDataHisBtn_Id").hide();
                    		Ext.getCmp("A9RawDataStartDate_Id").show();
                    		Ext.getCmp("A9RawDataEndDate_Id").show();
//                    		
//                    		
                			var record  = Ext.getCmp("A9RawDataGridPanel_Id").getSelectionModel().getSelection()[0];
                    		Ext.getCmp('A9RawDataDeviceCom_Id').setValue(record.data.deviceId);
                        	Ext.getCmp('A9RawDataDeviceCom_Id').setRawValue(record.data.deviceId);
                        	Ext.getCmp('A9RawDataGridPanel_Id').getStore().loadPage(1);
//                		}
                    }
              }, {
                  xtype: 'button',
                  text:'返回实时',
                  id:'A9RawDataAllBtn_Id',
                  pressed: true,
                  hidden: true,
                  handler: function (v, o) {
                	  Ext.getCmp("a9RawDataInfoPanelId").setTitle("实时数据");
                	  Ext.getCmp("A9RawDataAllBtn_Id").hide();
                	  Ext.getCmp("A9RawDataHisBtn_Id").show();
                	  Ext.getCmp("A9RawDataStartDate_Id").hide();
                	  Ext.getCmp("A9RawDataEndDate_Id").hide();
                	  Ext.getCmp("A9RawDataDeviceCom_Id").setValue('');
                	  Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
                  }
                }, {
                    id: 'A9RawDataListSelectRow_Id',//功图最新采集时间
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                }],
              
              
                
                layout: {
        	        type: 'hbox',
        	        pack: 'start',
        	        align: 'stretch'
        	    },
                items: [{
//                    region: 'center',
                	flex: 1,
                    border: false,
                    layout: 'fit',
                    id: "A9RawDataListPanel_Id", // 井名列表
                    collapsible: false, // 是否折叠
                    split: true // 竖折叠条
                }, {
//                    region: 'east',
//                    width: '65%',
                	flex: 2,
                    border: false,
                    header:false,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'right',
                    autoScroll:false,
                    scrollable: true,
                    height: 1500,
                    layout: {
                        type: 'vbox',
                        pack: 'start',
                        align: 'stretch'
                    },
                    items: [{
                    	height: 1500,
                        layout: {
                            type: 'vbox',
                            pack: 'start',
                            align: 'stretch'
                        },
                        items: [{
                            border: true,
                            margin: '0 0 0 0',
//                            height: 300,
                            flex: 1,
                            align:'stretch',
                            layout: 'fit',
                            id: 'A9RwaDataCurvePanel1_Id',
                            html: '<div id="A9RwaDataCurveChartDiv1_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if($("#A9RwaDataCurveChartDiv2_Id").highcharts()!=undefined){
                            			$("#A9RwaDataCurveChartDiv2_Id").highcharts().setSize($("#A9RwaDataCurveChartDiv2_Id").offsetWidth, $("#A9RwaDataCurveChartDiv2_Id").offsetHeight,true);
                            		}
                                }
                            }
                     },{
                         border: true,
                         margin: '1 0 0 0',
//                         height: 300,
                         flex: 1,
                         align:'stretch',
                         layout: 'fit',
                         id: 'A9RwaDataCurvePanel2_Id',
                         html: '<div id="A9RwaDataCurveChartDiv2_Id" style="width:100%;height:100%;"></div>',
                         listeners: {
                             resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	 if($("#A9RwaDataCurveChartDiv2_Id").highcharts()!=undefined){
                         			$("#A9RwaDataCurveChartDiv2_Id").highcharts().setSize($("#A9RwaDataCurveChartDiv2_Id").offsetWidth, $("#A9RwaDataCurveChartDiv2_Id").offsetHeight,true);
                         		}
                             }
                         }
                    },{
                       border: true,
                       margin: '1 0 0 0',
//                       height: 300,
                       flex: 1,
                       align:'stretch',
                       layout: 'fit',
                       id: 'A9RwaDataCurvePanel3_Id',
                       html: '<div id="A9RwaDataCurveChartDiv3_Id" style="width:100%;height:100%;"></div>',
                       listeners: {
                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	  if($("#A9RwaDataCurveChartDiv3_Id").highcharts()!=undefined){
                      			$("#A9RwaDataCurveChartDiv3_Id").highcharts().setSize($("#A9RwaDataCurveChartDiv3_Id").offsetWidth, $("#A9RwaDataCurveChartDiv3_Id").offsetHeight,true);
                      		}
                          }
                      }
                   },{
                	   border: true,
                	   margin: '1 0 0 0',
//                	   height: 300,
                	   flex: 1,
                	   align:'stretch',
                	   layout: 'fit',
                	   id: 'A9RwaDataCurvePanel4_Id',
                	   html: '<div id="A9RwaDataCurveChartDiv4_Id" style="width:100%;height:100%;"></div>',
                	   listeners: {
                		   resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                			   if($("#A9RwaDataCurveChartDiv4_Id").highcharts()!=undefined){
                       			$("#A9RwaDataCurveChartDiv4_Id").highcharts().setSize($("#A9RwaDataCurveChartDiv4_Id").offsetWidth, $("#A9RwaDataCurveChartDiv4_Id").offsetHeight,true);
                       		}
                		   }
                	   }
                   },{
                	   border: true,
                	   margin: '1 0 0 0',
//                	   height: 300,
                	   flex: 1,
                	   align:'stretch',
                	   layout: 'fit',
                	   id: 'A9RwaDataCurvePanel5_Id',
                	   html: '<div id="A9RwaDataCurveChartDiv5_Id" style="width:100%;height:100%;"></div>',
                	   listeners: {
                		   resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                			   if($("#A9RwaDataCurveChartDiv5_Id").highcharts()!=undefined){
                				   $("#A9RwaDataCurveChartDiv5_Id").highcharts().setSize($("#A9RwaDataCurveChartDiv5_Id").offsetWidth, $("#A9RwaDataCurveChartDiv5_Id").offsetHeight,true);
                       			}
                		   }
                	   }
                   }]
                    }]
                    
                }]
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {
    				a9RawDataWebsocketClose(a9RawDataWebsocket);
    			},
    			afterrender: function ( panel, eOpts) {
    				var baseUrl=getBaseUrl().replace("https","ws").replace("http","ws");
    				
    				var moduleCode = Ext.getCmp("frame_center_ids").getActiveTab().id;
    				if ('WebSocket' in window) {
    					a9RawDataWebsocket = new ReconnectingWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);//kafkaConfig_A9RawDataGridPanel
    					a9RawDataWebsocket.debug = true;
    					
    					a9RawDataWebsocket.reconnectInterval = 1000;
    					a9RawDataWebsocket.timeoutInterval = 2000;
    					
    					a9RawDataWebsocket.maxReconnectInterval = 30000;
    					
    					a9RawDataWebsocket.reconnectDecay=1.5;
    					
    					a9RawDataWebsocket.automaticOpen = true;
    					
//    					a9RawDataWebsocket.maxReconnectAttempts = 5;
    					
    					
    				}
    				else if ('MozWebSocket' in window) {
    					a9RawDataWebsocket = new MozWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
    				}
    				else {
    					a9RawDataWebsocket = new SockJS(baseRoot+"/sockjs/socketServer?module_Code="+moduleCode);
    				}
    				a9RawDataWebsocket.onopen = a9RawDataWebsocketOnOpen;
    				a9RawDataWebsocket.onmessage = a9RawDataWebsocketOnMessage;
    				a9RawDataWebsocket.onerror = a9RawDataWebsocketOnError;
    				a9RawDataWebsocket.onclose = a9RawDataWebsocketOnClose;
    			}
    		}
        });
        this.callParent(arguments);

    }
});


function a9RawDataWebsocketOnOpen(openEvt) {
//    alert(openEvt.Data);
}

function a9RawDataWebsocketOnMessage(evt) {
	var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
	var data=evt.data;
	if (activeId == "kafkaConfig_A9RawDataGridPanel") {
		if(data.toUpperCase()=='dataFresh'.toUpperCase()){//数据有刷新，更新界面
			var A9RawDataGridPanel=Ext.getCmp("A9RawDataGridPanel_Id");
			if(isNotVal(A9RawDataGridPanel)){
				A9RawDataGridPanel.getStore().load();
			}
		}
		
	}
}
function a9RawDataWebsocketOnOpen() {
//	alert("WebSocket连接成功");
}
function a9RawDataWebsocketOnError() {
//	alert("WebSocket连接发生错误");
}
function a9RawDataWebsocketOnClose() {
//	alert("WebSocket连接关闭");
}

function a9RawDataWebsocketClose(websocket) {
	if(websocket!=null){
		websocket.close();
	}
}



function createA9RawDataColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex == 'id') {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:true";
        }else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='deviceId'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function exportA9RawDataExcel() {
	var gridId = "A9RawDataGridPanel_Id";
    var url = context + '/kafkaConfigController/exportA9RawDataExcel';
    var id  = Ext.getCmp("A9RawDataGridPanel_Id").getSelectionModel().getSelection()[0].data.id;// 获取图形数据id
	var deviceId  = Ext.getCmp("A9RawDataGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceId;
    var selectedDeviceId = Ext.getCmp('A9RawDataDeviceCom_Id').getValue();
    
    var param = "&id=" + id + "&deviceId=" + deviceId + "&selectedDeviceId=" + selectedDeviceId;
    openExcelWindow(url + '?flag=true' + param);
};