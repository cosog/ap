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
            var showButtonCount=9999;

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
                        flex: 5,
                        renderer: function (value, e, o) {
                            e.tdStyle = "vertical-align:middle;";
                            if (isNotVal(value)) {
                                return "<span data-qtip=\"" + (value == undefined ? "" : value) + "\">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    }, {
                        text: loginUserLanguageResource.action,
                        align: 'center',
                        stopSelection: true,
                        xtype: 'widgetcolumn',
                        flex: 2,
//                        minWidth: 100, // 调整宽度以适应垂直排列
                        widget: {
                            xtype: 'container',
                            layout: {
                                type: 'vbox', // 改为垂直布局
                                align: 'center',
                                pack: 'center'
                            },
                            style: {
                                margin: '0 auto',
                                padding: '1px 0' // 增加上下内边距
                            },
                            // 动态设置高度，根据按钮数量调整
                            getWidgetRecord: function() {
                                return this.getWidgetRecord ? this.getWidgetRecord() : null;
                            }
                        },
                        // 关键：动态设置行高
                        renderer: function(value, meta, record) {
                            // 根据记录数据计算需要显示的按钮数量
                            var resolutionMode = record.data.resolutionMode;
                            var itemMeaning = record.data.itemMeaning || [];
                            var buttonCount = 0;
                            
                            if (resolutionMode == 1 && itemMeaning.length > 0 && itemMeaning.length<=showButtonCount) {
                                buttonCount = itemMeaning.length;
                            } else if (resolutionMode == 0 && itemMeaning.length > 0 && itemMeaning.length<=showButtonCount) {
                            	buttonCount = itemMeaning.length;
                            } else {
                                buttonCount = 1;
                            }
                            
                            // 根据按钮数量设置行高 (每个按钮高度约30px + 间距)
                            var rowHeight = Math.max(buttonCount * 20, 22);
                            meta.tdStyle = 'height: ' + rowHeight + 'px; vertical-align: middle;';
                            return value;
                        },
                        onWidgetAttach: function(column, widget, record) {
                            // 清空现有按钮
                            widget.removeAll();
                            
                            var resolutionMode = record.data.resolutionMode;
                            var itemMeaning = record.data.itemMeaning || [];
                            
                            var buttonsToShow = [];
                            
                            // 确定要显示的按钮配置
                            if (resolutionMode == 1 && itemMeaning.length <= showButtonCount) {
                                for (var i = 0; i < itemMeaning.length; i++) {
                                    buttonsToShow.push({
                                        text: itemMeaning[i][1],
                                        tooltip: itemMeaning[i][1],
                                        value: itemMeaning[i][0],
                                        index: i
                                    });
                                }
                            } else if (resolutionMode == 0 && itemMeaning.length > 0 && itemMeaning.length <= showButtonCount) {
                                for (var i = 0; i < itemMeaning.length; i++) {
                                    buttonsToShow.push({
                                        text: itemMeaning[i].status,
                                        tooltip: itemMeaning[i].status,
                                        value: itemMeaning[i].value,
                                        bitIndex: itemMeaning[i].bitIndex,
                                        index: i
                                    });
                                }
                            } 
//                            else if (resolutionMode == 0 && itemMeaning.length > 0) {
//                            	var switchingOpenValue=loginUserLanguageResource.switchingOpenValue;
//                            	var switchingCloseValue=loginUserLanguageResource.switchingCloseValue;
//                            	
//                            	for(var i = 0; i < itemMeaning.length; i++){
//                            		if(itemMeaning[i][0]==1 && isNotVal(itemMeaning[i][1]) ){
//                            			switchingOpenValue=itemMeaning[i][1];
//                            		}else if(itemMeaning[i][0]==0 && isNotVal(itemMeaning[i][1]) ){
//                            			switchingCloseValue=itemMeaning[i][1];
//                            		}
//                            	}
//                            	
//                                buttonsToShow.push({
//                                    text: switchingOpenValue,
//                                    tooltip: switchingOpenValue,
//                                    value: 'open',
//                                    index: 0
//                                });
//                                buttonsToShow.push({
//                                    text: switchingCloseValue,
//                                    tooltip: switchingCloseValue,
//                                    value: 'close',
//                                    index: 1
//                                });
//                            } 
                            
                            else {
                                buttonsToShow.push({
                                    text: loginUserLanguageResource.set,
                                    tooltip: loginUserLanguageResource.set,
                                    value: '',
                                    index: 0
                                });
                            }
                            
                            // 创建按钮
                            buttonsToShow.forEach(function(btnConfig) {
                                var button = widget.add({
                                    xtype: 'button',
                                    text: btnConfig.text,
                                    height: 20,
                                    width: 80,
                                    margin: '2 0', // 按钮间垂直间距
                                    value: btnConfig.value,
                                    index: btnConfig.index,
                                    disabled: !(commStatus > 0 && isControl == 1),
                                    handler: function(btn) {
                                        controlBtnHandler(btn, btn.index,showButtonCount);
                                    }
                                });
                            });
                        }
                    }],
                    listeners: {
                        columnresize: function (ct, column, width, eOpts) {
                            // 列宽调整处理
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
            var deviceType = getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
            var new_params = {
                deviceId: deviceId,
                deviceName: deviceName,
                deviceType: deviceType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});