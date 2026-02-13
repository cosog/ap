Ext.define("AP.view.acquisitionUnit.ModbusProtocolReportUnitClasses0ConfigInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportUnitClasses0ConfigInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	items:[{
        		xtype: 'tabpanel',
        		id:"ModbusProtocolReportUnitReportConfigPanel_Id",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'top',
        		items:[{
                    title: loginUserLanguageResource.singleDeviceReport,
                    iconCls: 'check3',
                    id: 'ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                        title: loginUserLanguageResource.hourlyReport,
                        iconCls: 'check3',
                        id: 'ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'west',
                            width: '20%',
                            layout: 'fit',
                            border: false,
                            id: 'ReportUnitSingleWellDailyReportTemplateListPanel_Id',
                            title: '报表模板列表',
                            header: false,
                            split: true,
                            collapsible: true
                    	}, {
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: loginUserLanguageResource.deviceHourlyReportTemplate,
                                id: "ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="ReportUnitSingleWellDailyReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellDailyReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        if (singleWellDailyReportTemplateHandsontableHelper != null && singleWellDailyReportTemplateHandsontableHelper.hot != null && singleWellDailyReportTemplateHandsontableHelper.hot != undefined) {
                                            var newWidth = width;
                                            var newHeight = height;
                                            var header = thisPanel.getHeader();
                                            if (header) {
                                                newHeight = newHeight - header.lastBox.height - 2;
                                            }
                                            singleWellDailyReportTemplateHandsontableHelper.hot.updateSettings({
                                                width: newWidth,
                                                height: newHeight
                                            });
                                        }
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '50%',
                                title: loginUserLanguageResource.deviceHourlyReportContentConfig,
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="ReportUnitSingleWellDailyReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        if (singleWellDailyReportTemplateContentHandsontableHelper != null && singleWellDailyReportTemplateContentHandsontableHelper.hot != null && singleWellDailyReportTemplateContentHandsontableHelper.hot != undefined) {
                                            var newWidth = width;
                                            var newHeight = height;
                                            var header = thisPanel.getHeader();
                                            if (header) {
                                                newHeight = newHeight - header.lastBox.height - 2;
                                            }
                                            singleWellDailyReportTemplateContentHandsontableHelper.hot.updateSettings({
                                                width: newWidth,
                                                height: newHeight
                                            });
                                        }
                                    }
                                }
                        	}]
                    	}]
                    }, {
                        title: loginUserLanguageResource.dailyReport,
                        id: 'ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'west',
                            width: '20%',
                            layout: 'fit',
                            border: false,
                            id: 'ReportUnitSingleWellRangeReportTemplateListPanel_Id',
                            title: '报表模板列表',
                            header: false,
                            split: true,
                            collapsible: true
                    	}, {
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: loginUserLanguageResource.deviceDailyReportTemplate,
                                id: "ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="ReportUnitSingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        if (singleWellRangeReportTemplateHandsontableHelper != null && singleWellRangeReportTemplateHandsontableHelper.hot != null && singleWellRangeReportTemplateHandsontableHelper.hot != undefined) {
                                            var newWidth = width;
                                            var newHeight = height;
                                            var header = thisPanel.getHeader();
                                            if (header) {
                                                newHeight = newHeight - header.lastBox.height - 2;
                                            }
                                            singleWellRangeReportTemplateHandsontableHelper.hot.updateSettings({
                                                width: newWidth,
                                                height: newHeight
                                            });
                                        }
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '60%',
                                title: loginUserLanguageResource.deviceDailyReportContentConfig,
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="ReportUnitSingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        if (singleWellRangeReportTemplateContentHandsontableHelper != null && singleWellRangeReportTemplateContentHandsontableHelper.hot != null && singleWellRangeReportTemplateContentHandsontableHelper.hot != undefined) {
                                            var newWidth = width;
                                            var newHeight = height;
                                            var header = thisPanel.getHeader();
                                            if (header) {
                                                newHeight = newHeight - header.lastBox.height - 2;
                                            }
                                            singleWellRangeReportTemplateContentHandsontableHelper.hot.updateSettings({
                                                width: newWidth,
                                                height: newHeight
                                            });
                                        }
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                    		if(oldCard!=undefined){
                    			oldCard.setIconCls(null);
                    	    }
                    	    if(newCard!=undefined){
                    	    	newCard.setIconCls('check3');				
                    	    }
            			},
            			tabchange: function (tabPanel, newCard, oldCard, obj) {
                            var unitTreeSelection = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
                            var selectedUnitId = 0;
                            if (unitTreeSelection.length > 0) {
                                var record = unitTreeSelection[0];
                                if (record.data.classes == 0 && isNotVal(record.data.children) && record.data.children.length > 0) {
                                    selectedUnitId = record.data.children[0].id;
                                } else if (record.data.classes == 1) {
                                    selectedUnitCode = record.data.code;
                                    selectedUnitId = record.data.id;
                                }
                            }

                            if (newCard.id == "ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id") {
                                var ReportUnitSingleWellDailyReportTemplateListGridPanel = Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
                                if (isNotVal(ReportUnitSingleWellDailyReportTemplateListGridPanel)) {
                                    ReportUnitSingleWellDailyReportTemplateListGridPanel.getStore().load();
                                } else {
                                    Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore')
                                }
//                                CreateSingleWellDailyReportTotalItemsInfoTable(record.data.calculateType, selectedUnitId, record.data.text, record.data.classes);

                            } else if (newCard.id == "ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id") {
                                var ReportUnitSingleWellRangeReportTemplateListGridPanel = Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
                                if (isNotVal(ReportUnitSingleWellRangeReportTemplateListGridPanel)) {
                                    ReportUnitSingleWellRangeReportTemplateListGridPanel.getStore().load();
                                } else {
                                    Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore')
                                }
                                //                                	CreateSingleWellRangeReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
                            }
                        }
                    }
                }, {
                    title: loginUserLanguageResource.areaReport,
                    id: 'ModbusProtocolReportUnitProductionReportTemplatePanel_Id',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                        title: loginUserLanguageResource.dailyReport,
                        iconCls: 'check3',
                        id: 'ModbusProtocolReportUnitProductionRangeReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'west',
                            width: '20%',
                            layout: 'fit',
                            border: false,
                            id: 'ReportUnitProductionReportTemplateListPanel_Id',
                            title: '报表模板列表',
                            header: false,
                            split: true,
                            collapsible: true
                    	}, {
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: '区间日报模板',
                                id: "ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="ModbusProtocolReportUnitProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitProductionTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        if (productionReportTemplateHandsontableHelper != null && productionReportTemplateHandsontableHelper.hot != null && productionReportTemplateHandsontableHelper.hot != undefined) {
                                            var newWidth = width;
                                            var newHeight = height;
                                            var header = thisPanel.getHeader();
                                            if (header) {
                                                newHeight = newHeight - header.lastBox.height - 2;
                                            }
                                            productionReportTemplateHandsontableHelper.hot.updateSettings({
                                                width: newWidth,
                                                height: newHeight
                                            });
                                        }
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '50%',
                                title: loginUserLanguageResource.deviceDailyReportContentConfig,
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="ModbusProtocolProductionReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolProductionReportUnitContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        if (productionReportTemplateContentHandsontableHelper != null && productionReportTemplateContentHandsontableHelper.hot != null && productionReportTemplateContentHandsontableHelper.hot != undefined) {
                                            var newWidth = width;
                                            var newHeight = height;
                                            var header = thisPanel.getHeader();
                                            if (header) {
                                                newHeight = newHeight - header.lastBox.height - 2;
                                            }
                                            productionReportTemplateContentHandsontableHelper.hot.updateSettings({
                                                width: newWidth,
                                                height: newHeight
                                            });
                                        }
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                    		if(oldCard!=undefined){
                    			oldCard.setIconCls(null);
                    	    }
                    	    if(newCard!=undefined){
                    	    	newCard.setIconCls('check3');				
                    	    }
            			},
            			tabchange: function (tabPanel, newCard, oldCard, obj) {

                        }
                    }
                }],
        		listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                		if(oldCard!=undefined){
                			oldCard.setIconCls(null);
                	    }
                	    if(newCard!=undefined){
                	    	newCard.setIconCls('check3');				
                	    }
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
                        var unitTreeSelection = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
                        var selectedUnitId = 0;
                        if (unitTreeSelection.length > 0) {
                            var record = unitTreeSelection[0];
                            if (record.data.classes == 0 && isNotVal(record.data.children) && record.data.children.length > 0) {
                                selectedUnitId = record.data.children[0].id;
                            } else if (record.data.classes == 1) {
                                selectedUnitCode = record.data.code;
                                selectedUnitId = record.data.id;
                            }
                        }

                        if (newCard.id == "ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id") {
                            var singleWellReportActiveId = Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
                            if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id') {
                                var ReportUnitSingleWellDailyReportTemplateListGridPanel = Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
                                if (isNotVal(ReportUnitSingleWellDailyReportTemplateListGridPanel)) {
                                    ReportUnitSingleWellDailyReportTemplateListGridPanel.getStore().load();
                                } else {
                                    Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore')
                                }

                            } else if (singleWellReportActiveId == 'ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id') {
                                var ReportUnitSingleWellRangeReportTemplateListGridPanel = Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
                                if (isNotVal(ReportUnitSingleWellRangeReportTemplateListGridPanel)) {
                                    ReportUnitSingleWellRangeReportTemplateListGridPanel.getStore().load();
                                } else {
                                    Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore')
                                }
                            }
                        } else if (newCard.id == "ModbusProtocolReportUnitProductionReportTemplatePanel_Id") {
                            var ReportUnitProductionReportTemplateListGridPanel = Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id");
                            if (isNotVal(ReportUnitProductionReportTemplateListGridPanel)) {
                                ReportUnitProductionReportTemplateListGridPanel.getStore().load();
                            } else {
                                Ext.create('AP.store.acquisitionUnit.ModbusProtocolProductionReportTemplateStore')
                            }
                        }
                    }
                }
        	}],
        	listeners: {
    			beforeclose: function ( panel, eOpts) {
    				
    			},
    			afterrender: function ( panel, eOpts) {
    				
    			}
    		}
        });
        me.callParent(arguments);
    }
});