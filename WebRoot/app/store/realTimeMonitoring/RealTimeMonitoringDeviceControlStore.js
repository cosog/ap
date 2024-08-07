Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceControlStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RealTimeMonitoringDeviceControlStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceControlData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
        	var get_rawData = store.proxy.reader.rawData;
            var isControl = get_rawData.isControl;
            var commStatus = get_rawData.commStatus;

            var controlGridPanel = Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
            if (!isNotVal(controlGridPanel)) {
                controlGridPanel = Ext.create('Ext.grid.Panel', {
                    id: 'RealTimeMonitoringControlDataGridPanel_Id',
                    border: false,
                    columnLines: true,
                    forceFit: false,
                    store: store,
                    scrollOffset: 0,
                    columns: [{
                            header: '控制项',
                            dataIndex: 'item',
                            align: 'left',
                            flex: 6,
                            renderer: function (value, e, o) {
                                e.tdStyle = "vertical-align:middle;";
                                if (isNotVal(value)) {
                                    return "<span data-qtip=\"" + (value == undefined ? "" : value) + "\">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
			        }, 
			        
			        {
			        	text: '动作',
			            align: 'center',
			            stopSelection: true,
			            xtype: 'widgetcolumn',
			            widget: {
			            	xtype: 'toolbar',
			            	dock: 'top',
			            	style: 'margin:0;padding:0',
			            	ui: 'footer',
			            	height: 25,
			            	minWidth: 85,
			            	items:[{
				            	  xtype: 'button',
//				                   _btnText: "myRandomText",
				                   text:'设置',
				                   height: 25,
				                   width: 38,
				                   defaultBindProperty: null, //important
				                   handler: function(widgetColumn) {
//				                         var record = widgetColumn.getWidgetRecord();
//				                         console.log("Got data!", record);
				                   },
				                   listeners: {
				                         beforerender: function(widgetColumn){
//				                             var record = widgetColumn.getWidgetRecord();
//				                             widgetColumn.setText( widgetColumn._btnText ); //can be mixed with the row data if needed
				                         }
				                    }
			            	},{
				            	  xtype: 'button',
//				                   _btnText: "myRandomText",
				                   text:'设置2',
				                   height: 25,
				                   width: 38,
				                   defaultBindProperty: null, //important
				                   handler: function(widgetColumn) {
//				                         var record = widgetColumn.getWidgetRecord();
//				                         console.log("Got data!", record);
				                   },
				                   listeners: {
				                         beforerender: function(btn){
				                             var record = btn.up().getWidgetRecord();
				                             btn.setDisabled(true);
//				                             btn.setHidden(true);
//				                             widgetColumn.setText( widgetColumn._btnText ); //can be mixed with the row data if needed
				                         }
				                    }
			            	}],
			            	listeners: {
		                         beforerender: function(widgetColumn){
//		                             var record = widgetColumn.getWidgetRecord();
//		                             widgetColumn.setText( widgetColumn._btnText ); //can be mixed with the row data if needed
		                         }
		                    }
			            }
			        }
			        
			        
			        
			        
//			        {
//                            header: '动作',
//                            dataIndex: 'operation',
//                            align: 'center',
//                            flex: 2,
//                            minWidth: 93,
//                            renderer: function (value, e, o) {
//                            	var id = e.record.id;
//                            	Ext.defer(function () {
//                                    Ext.widget('toolbar', {
//                                        renderTo: id,
//                                        dock: 'top',
//                                        style: 'margin:0;padding:0',
//                                        ui: 'footer',
//                                        height: 25,
//                                        minWidth: 85,
//                                        items: [{
//                                            xtype: 'button',
//                                            height: 25,
//                                            width: 38,
//                                            style: 'margin:1',
//                                            text: '按钮1',
//                                            handler: function () {
//                                            	
//                                            }
//		                            }, {
//                                            xtype: 'button',
//                                            height: 25,
//                                            width: 38,
//                                            style: 'margin:0',
//                                            text: '按钮2',
//                                            handler: function () {
//                                            	
//                                            }
//		                            	}]
//                                    });
//                                }, 50);
//                            	return Ext.String.format('<div id="{0}"></div>', id);
//                            }
//			        }
//			        ,{
//			        	header: '操作2',
//                        dataIndex: 'operation2',
//                        align: 'center',
//                        flex: 2
//			        }
			        ],
			        listeners: {
			        	columnresize: function ( ct, column, width, eOpts ) {
			        		if(column.dataIndex=='operation'){
//			        			alert(width);
//			        			var aa = Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
//			                    if (isNotVal(aa)) {
//			                    	aa.store.reload();
//			                    }
			        		}
			        	}
			        }
                });
                Ext.getCmp("RealTimeMonitoringRightControlPanel").add(controlGridPanel);
            }
            Ext.getCmp("RealTimeMonitoringTabPanel").getEl().unmask();
            Ext.getCmp("RealTimeMonitoringInfoPanel_Id").getEl().unmask();
        },
        beforeload: function (store, options) {
//        	Ext.getCmp("RealTimeMonitoringRightControlPanel").removeAll();
        	var deviceName = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
            var deviceId = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
            var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
            var new_params = {
                deviceId: deviceId,
                deviceName: deviceName,
                deviceType: deviceType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});