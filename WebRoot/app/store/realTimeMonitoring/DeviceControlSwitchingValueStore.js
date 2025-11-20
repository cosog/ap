Ext.define('AP.store.realTimeMonitoring.DeviceControlSwitchingValueStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.DeviceControlSwitchingValueStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceSwitchingValueControlData',
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

            var controlGridPanel = Ext.getCmp("RealTimeMonitoringEnumValueControlDataGridPanel_Id");
            if (!isNotVal(controlGridPanel)) {
                controlGridPanel = Ext.create('Ext.grid.Panel', {
                    id: 'RealTimeMonitoringEnumValueControlDataGridPanel_Id',
                    border: false,
                    columnLines: true,
                    forceFit: false,
                    store: store,
                    scrollOffset: 0,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex:2,
                        width: getLabelWidth(loginUserLanguageResource.idx,loginUserLanguage)+'px',
                        xtype: 'rownumberer'
                    },{
                        header: loginUserLanguageResource.meaning,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 9,
                        dataIndex: 'meaning'
                    },{
			        	text: loginUserLanguageResource.action,
			            align: 'center',
			            stopSelection: true,
			            xtype: 'widgetcolumn',
			            flex:9,
			            widget: {
			                xtype: 'toolbar',
			                layout: {
			                    type: 'hbox',
			                    pack: 'center'
			                },
			                style: {
			                    margin: '0 auto',
			                    padding: '0'
			                },
			                ui: 'footer',
			                height: 20,
			                minWidth: 120,
			            	
			            	items:[{
				            	  xtype: 'button',
				                   text:loginUserLanguageResource.set,
				                   height: 20,
				                   width: 80,
				                   defaultBindProperty: null, //important
				                   handler: function(btn) {
				                	   switchingValueControlBtnHandler(btn);
				                   },
				                   listeners: {
				                         beforerender: function(btn){
				                        	 renderSwitchingValueControlBtn(btn);
				                         }
				                    }
			            	}],
			            	listeners: {
		                         beforerender: function(widgetColumn){
		                         }
		                    }
			            }
			        },{
                        header: loginUserLanguageResource.value,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'value',
                        hidden: true
                    },{
                        header: loginUserLanguageResource.bitIndex,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'bitIndex',
                        hidden: true
                    }],
			        listeners: {
			        	columnresize: function ( ct, column, width, eOpts ) {
			        		
			        	}
			        }
                });
                Ext.getCmp("DeviceControlValueTablePanel_Id").add(controlGridPanel);
            }
        },
        beforeload: function (store, options) {
        	var deviceId= Ext.getCmp('DeviceControlDeviceId_Id').getValue();
        	var deviceName= Ext.getCmp('DeviceControlDeviceName_Id').getValue();
        	var deviceType= Ext.getCmp('DeviceControlDeviceType_Id').getValue();
        	var controlType= Ext.getCmp('DeviceControlType_Id').getValue();
        	var storeDataType= Ext.getCmp('DeviceControlStoreDataType_Id').getValue();
        	var resolutionMode= Ext.getCmp('DeviceControlShowType_Id').getValue();
        	var quantity= Ext.getCmp('DeviceControlQuantity_Id').getValue();
            var new_params = {
            		deviceId: deviceId,
                	deviceName: deviceName,
                	deviceType: deviceType,
                    controlType: controlType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
