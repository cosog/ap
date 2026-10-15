Ext.define("AP.view.acquisitionUnit.ModbusProtocolReportUnitClasses2ConfigInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportUnitClasses2ConfigInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	items:[{
        		xtype: 'tabpanel',
        		id:"ModbusProtocolReportUnitClasses2ReportConfigPanel_Id",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'top',
        		items:[{
                    title: loginUserLanguageResource.singleDeviceReport,
                    id: 'ModbusProtocolReportUnitClasses2SingleWellReportTemplatePanel_Id',
                    layout: "border",
                    border: false,
                    items: [{
                        region: 'west',
                        width: '20%',
                        layout: 'fit',
                        border: false,
                        id: 'ReportUnitClasses2SingleWellRangeReportTemplateListPanel_Id',
                        header: false,
                        hidden:true,
                        split: true,
                        collapsible: true
                	}, {
                        region: 'center',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'center',
                            title: loginUserLanguageResource.deviceDailyReportTemplate,
                            id: "ReportUnitClasses2SingleWellRangeReportTemplateTableInfoPanel_Id",
                            layout: 'fit',
                            border: false,
                            html: '<div class="ReportUnitClasses2SingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitClasses2SingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    if (singleWellRangeClasses2ReportTemplateHandsontableHelper != null && singleWellRangeClasses2ReportTemplateHandsontableHelper.hot != null && singleWellRangeClasses2ReportTemplateHandsontableHelper.hot != undefined) {
                                        var newWidth = width;
                                        var newHeight = height;
                                        var header = thisPanel.getHeader();
                                        if (header) {
                                            newHeight = newHeight - header.lastBox.height - 2;
                                        }
                                        singleWellRangeClasses2ReportTemplateHandsontableHelper.hot.updateSettings({
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
                            id: "ReportUnitClasses2SingleWellRangeReportContentConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html: '<div class="ReportUnitClasses2SingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitClasses2SingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    if (singleWellRangeClasses2ReportTemplateContentHandsontableHelper != null && singleWellRangeClasses2ReportTemplateContentHandsontableHelper.hot != null && singleWellRangeClasses2ReportTemplateContentHandsontableHelper.hot != undefined) {
                                        var newWidth = width;
                                        var newHeight = height;
                                        var header = thisPanel.getHeader();
                                        if (header) {
                                            newHeight = newHeight - header.lastBox.height - 2;
                                        }
                                        singleWellRangeClasses2ReportTemplateContentHandsontableHelper.hot.updateSettings({
                                            width: newWidth,
                                            height: newHeight
                                        });
                                    }
                                }
                            }
                    	}]
                	}]
                }, {
                    title: loginUserLanguageResource.areaReport,
                    id: 'ModbusProtocolReportUnitClasses2ProductionReportTemplatePanel_Id',
                    layout: "border",
                    border: false,
                    items: [{
                        region: 'west',
                        width: '20%',
                        layout: 'fit',
                        border: false,
                        id: 'ReportUnitClasses2ProductionReportTemplateListPanel_Id',
                        header: false,
                        hidden:true,
                        split: true,
                        collapsible: true
                	}, {
                        region: 'center',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'center',
                            title: loginUserLanguageResource.areaDailyReportTemplate,
                            id: "ModbusProtocolReportUnitClasses2ProductionTemplateTableInfoPanel_Id",
                            layout: 'fit',
                            border: false,
                            html: '<div class="ModbusProtocolReportUnitClasses2ProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitClasses2ProductionTemplateTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    if (productionReportTemplateClasses2HandsontableHelper != null && productionReportTemplateClasses2HandsontableHelper.hot != null && productionReportTemplateClasses2HandsontableHelper.hot != undefined) {
                                        var newWidth = width;
                                        var newHeight = height;
                                        var header = thisPanel.getHeader();
                                        if (header) {
                                            newHeight = newHeight - header.lastBox.height - 2;
                                        }
                                        productionReportTemplateClasses2HandsontableHelper.hot.updateSettings({
                                            width: newWidth,
                                            height: newHeight
                                        });
                                    }
                                }
                            }
                    	}, {
                            region: 'south',
                            height: '50%',
                            title: loginUserLanguageResource.areaDailyReportContentConfig,
                            collapsible: true,
                            split: true,
                            layout: 'fit',
                            border: false,
                            id: "ModbusProtocolProductionReportUnitClasses2ContentConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html: '<div class="ModbusProtocolProductionReportUnitClasses2ContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolProductionReportUnitClasses2ContentConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    if (productionReportTemplateClasses2ContentHandsontableHelper != null && productionReportTemplateClasses2ContentHandsontableHelper.hot != null && productionReportTemplateClasses2ContentHandsontableHelper.hot != undefined) {
                                        var newWidth = width;
                                        var newHeight = height;
                                        var header = thisPanel.getHeader();
                                        if (header) {
                                            newHeight = newHeight - header.lastBox.height - 2;
                                        }
                                        productionReportTemplateClasses2ContentHandsontableHelper.hot.updateSettings({
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
                        
                        CreateClasses2ReportTemplateInfoTable();
                        CreateClasses2ReportContentInfoTable();
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