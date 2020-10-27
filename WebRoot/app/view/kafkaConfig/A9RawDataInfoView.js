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
//                	Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
                }
            }
        });
    	Ext.apply(me, {
    		items: [{
                layout: "border",
                border: false,
                id:'a9RawDataInfoPanelId',
                title: '实时数据',
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
//                    		Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
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
//                    		Ext.getCmp("A9RawDataGridPanel_Id").getStore().loadPage(1);
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
                }],
                items: [{
                    region: 'center',
                    border: false,
                    layout: 'fit',
                    id: "KafkaConfigWellListPanel_Id", // 井名列表
                    collapsible: false, // 是否折叠
                    split: true // 竖折叠条
                }, {
                    region: 'east',
                    width: '65%',
                    border: false,
                    header:false,
                    collapsible: true,
                    split: true,
                    autoScroll:true,
                    scrollable: true,
                    height: 1500,
                    layout: {
                        type: 'vbox',
                        pack: 'start',
                        align: 'stretch'
                    },
                    items: [{
                        border: true,
                        margin: '0 0 0 0',
                        height: 300,
//                        flex: 1,
                        layout: 'fit',
                        id: 'A9RwaDataCurvePanel1_Id',
                        html: '<div id="A9RwaDataCurveChartDiv1_Id" style="width:100%;height:100%;"></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	if ($("#A9RwaDataCurveChartDiv1_Id").highcharts() != undefined) {
                                    $("#A9RwaDataCurveChartDiv1_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                }
                            }
                        }
                 },{
                     border: true,
                     margin: '1 0 0 0',
                     height: 300,
//                     flex: 1,
                     layout: 'fit',
                     id: 'A9RwaDataCurvePanel2_Id',
                     html: '<div id="A9RwaDataCurveChartDiv2_Id" style="width:100%;height:100%;"></div>',
                     listeners: {
                         resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                         	if ($("#A9RwaDataCurveChartDiv2_Id").highcharts() != undefined) {
                                 $("#A9RwaDataCurveChartDiv2_Id").highcharts().setSize(adjWidth, adjHeight, true);
                             }
                         }
                     }
                },{
                   border: true,
                   margin: '1 0 0 0',
                   height: 300,
//                  flex: 1,
                   layout: 'fit',
                   id: 'A9RwaDataCurvePanel3_Id',
                   html: '<div id="A9RwaDataCurveChartDiv3_Id" style="width:100%;height:100%;"></div>',
                   listeners: {
                      resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                      	if ($("#A9RwaDataCurveChartDiv3_Id").highcharts() != undefined) {
                              $("#A9RwaDataCurveChartDiv3_Id").highcharts().setSize(adjWidth, adjHeight, true);
                          }
                      }
                  }
               },{
            	   border: true,
            	   margin: '1 0 0 0',
            	   height: 300,
//               flex: 1,
            	   layout: 'fit',
            	   id: 'A9RwaDataCurvePanel4_Id',
            	   html: '<div id="A9RwaDataCurveChartDiv4_Id" style="width:100%;height:100%;"></div>',
            	   listeners: {
            		   resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            			   if ($("#A9RwaDataCurveChartDiv4_Id").highcharts() != undefined) {
            				   $("#A9RwaDataCurveChartDiv4_Id").highcharts().setSize(adjWidth, adjHeight, true);
            			   }
            		   }
            	   }
               },{
            	   border: true,
            	   margin: '1 0 0 0',
            	   height: 300,
//            flex: 1,
            	   layout: 'fit',
            	   id: 'A9RwaDataCurvePanel5_Id',
            	   html: '<div id="A9RwaDataCurveChartDiv5_Id" style="width:100%;height:100%;"></div>',
            	   listeners: {
            		   resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            			   if ($("#A9RwaDataCurveChartDiv5_Id").highcharts() != undefined) {
            				   $("#A9RwaDataCurveChartDiv5_Id").highcharts().setSize(adjWidth, adjHeight, true);
            			   }
            		   }
            	   }
               }]
                }]
    		}]
        });
        this.callParent(arguments);

    }
});

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