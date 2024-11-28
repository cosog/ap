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
                            header: loginUserLanguageResource.controlItem,
                            dataIndex: 'item',
                            align: 'left',
                            flex: 6,
                            renderer: function (value, e, o) {
                                e.tdStyle = "vertical-align:middle;";
                                if (isNotVal(value)) {
                                    return "<span data-qtip=\"" + (value == undefined ? "" : value) + "\">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
			        },{
			        	text: '动作',
			            align: 'center',
			            stopSelection: true,
			            xtype: 'widgetcolumn',
			            widget: {
			            	xtype: 'toolbar',
			            	dock: 'top',
			            	style: 'margin:0;padding:0',
			            	ui: 'footer',
			            	height: 20,
			            	minWidth: 85,
			            	items:[{
				            	  xtype: 'button',
				                   text:'设置',
				                   height: 20,
				                   width: 38,
				                   defaultBindProperty: null, //important
				                   handler: function(btn) {
				                	   controlBtnHandler(btn,0);
				                   },
				                   listeners: {
				                         beforerender: function(btn){
				                        	 renderControlBtn(btn,0);
				                         }
				                    }
			            	},{
				            	  xtype: 'button',
				                   text:'设置2',
				                   height: 20,
				                   width: 38,
				                   defaultBindProperty: null, //important
				                   handler: function(btn) {
				                	   controlBtnHandler(btn,1);
				                   },
				                   listeners: {
				                         beforerender: function(btn){
				                        	 renderControlBtn(btn,1);
				                         }
				                    }
			            	}],
			            	listeners: {
		                         beforerender: function(widgetColumn){
//		                             var record = widgetColumn.getWidgetRecord();
		                         }
		                    }
			            }
			        }],
			        listeners: {
			        	columnresize: function ( ct, column, width, eOpts ) {
			        		
			        	}
			        }
                });
                Ext.getCmp("RealTimeMonitoringRightControlPanel").add(controlGridPanel);
            }
            Ext.getCmp("RealTimeMonitoringTabPanel").getEl().unmask();
            Ext.getCmp("RealTimeMonitoringInfoPanel_Id").getEl().unmask();
        },
        beforeload: function (store, options) {
        	Ext.getCmp("RealTimeMonitoringRightControlPanel").removeAll();
        	var deviceName = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
            var deviceId = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
            var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
            var new_params = {
                deviceId: deviceId,
                deviceName: deviceName,
                deviceType: deviceType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

