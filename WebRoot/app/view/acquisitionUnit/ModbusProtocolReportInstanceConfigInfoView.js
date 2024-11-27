//var reportInstanceSingleWellRangeReportTemplateHandsontableHelper=null;
//var reportInstanceSingleWellRangeReportContentHandsontableHelper=null;
//
//var reportInstanceSingleWellDailyReportTemplateHandsontableHelper=null;
//var reportInstanceSingleWellDailyReportContentHandsontableHelper=null;
//
//var reportInstanceProductionTemplateHandsontableHelper=null;
//var reportInstanceProductionTemplateContentHandsontableHelper=null;
//
//var protocolReportInstancePropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolReportInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportInstanceConfigInfoView',
    layout: "fit",
    id:'modbusProtocolReportInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolReportInstanceTreeSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加实例',
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolReportInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveReportInstanceData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolReportInstanceWindow");
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.importData,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'import',
        			handler: function (v, o) {
        				var selectedDeviceTypeName="";
        				var selectedDeviceTypeId="";
        				var tabTreeStore = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getStore();
        				var count=tabTreeStore.getCount();
        				var tabTreeSelection = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        				var rec=null;
        				if (tabTreeSelection.length > 0) {
        					rec=tabTreeSelection[0];
        					selectedDeviceTypeName=foreachAndSearchTabAbsolutePath(tabTreeStore.data.items,tabTreeSelection[0].data.deviceTypeId);
        					selectedDeviceTypeId=tabTreeSelection[0].data.deviceTypeId;
        				} else {
        					if(count>0){
        						rec=orgTreeStore.getAt(0);
        						selectedDeviceTypeName=orgTreeStore.getAt(0).data.text;
        						selectedDeviceTypeId=orgTreeStore.getAt(0).data.deviceTypeId;
        					}
        				}
        				var window = Ext.create("AP.view.acquisitionUnit.ImportReportInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportReportInstanceWinTabLabel_Id").setHtml("实例将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportReportInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportReportInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	border: true,
                	region: 'west',
                	width:'20%',
                    layout: "border",
                    border: false,
                    header: false,
                    split: true,
                    collapsible: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:loginUserLanguageResource.reportInstanceList,
                    	layout: 'fit',
                    	border: false,
                    	id:"ModbusProtocolReportInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:loginUserLanguageResource.properties,
                    	border: false,
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolReportInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstancePropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if (protocolReportInstancePropertiesHandsontableHelper != null && protocolReportInstancePropertiesHandsontableHelper.hot != null && protocolReportInstancePropertiesHandsontableHelper.hot != undefined) {
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolReportInstancePropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                                }
                            }
                        }
                    }]
                },{
                	region: 'center',
                	xtype: 'tabpanel',
                	id:"ModbusProtocolReportInstanceReportTemplateTabPanel_Id",
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	title:loginUserLanguageResource.singleDeviceReport,
                    	iconCls: 'check3',
                    	id:'ModbusProtocolReportInstanceSingleWellReportTemplatePanel_Id',
                    	xtype: 'tabpanel',
                    	activeTab: 0,
                        border: false,
                        tabPosition: 'top',
                        items: [{
                        	id:'ModbusProtocolReportInstanceSingleWellDailyReportTemplatePanel_Id',
                        	title:loginUserLanguageResource.hourlyReport,
                        	iconCls: 'check3',
                        	border: false,
                        	region: 'center',
                        	layout: "border",
                        	items: [{
                        		region: 'center',
                        		layout: "border",
                        		items: [{
                            		region: 'center',
                            		title:loginUserLanguageResource.deviceHourlyReportTemplate,
                            		id:"ReportInstanceSingleWellDailyReportTemplateTableInfoPanel_Id",
                                    layout: 'fit',
                                    border: false,
                                    html:'<div class="ReportInstanceSingleWellDailyReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellDailyReportTemplateTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (reportInstanceSingleWellDailyReportTemplateHandsontableHelper != null && reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot != null && reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                            	reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	},{
                            		region: 'south',
                                	height:'50%',
                                	title:'单井班报表内容',
                                	border: false,
                                	collapsible: true,
                                    split: true,
                                	layout: 'fit',
                                	id:"ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id",
                                    layout: 'fit',
                                    html:'<div class="ReportInstanceSingleWellDailyReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellDailyReportContentConfigTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (reportInstanceSingleWellDailyReportContentHandsontableHelper != null && reportInstanceSingleWellDailyReportContentHandsontableHelper.hot != null && reportInstanceSingleWellDailyReportContentHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		reportInstanceSingleWellDailyReportContentHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	}]
                        	}]
                        },{
                        	id:'ModbusProtocolReportInstanceSingleWellRangeReportTemplatePanel_Id',
                        	title:loginUserLanguageResource.dailyReport,
                        	border: false,
                        	region: 'center',
                        	layout: "border",
                        	items: [{
                        		region: 'center',
                        		layout: "border",
                        		items: [{
                            		region: 'center',
                            		title:loginUserLanguageResource.deviceDailyReportTemplate,
                            		id:"ReportInstanceSingleWellRangeReportTemplateTableInfoPanel_Id",
                                    layout: 'fit',
                                    border: false,
                                    html:'<div class="ReportInstanceSingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (reportInstanceSingleWellRangeReportTemplateHandsontableHelper != null && reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot != null && reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                            	reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	},{
                            		region: 'south',
                                	height:'50%',
                                	title:'单井日报表内容',
                                	border: false,
                                	collapsible: true,
                                    split: true,
                                	layout: 'fit',
                                	id:"ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id",
                                    layout: 'fit',
                                    html:'<div class="ReportInstanceSingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (reportInstanceSingleWellRangeReportContentHandsontableHelper != null && reportInstanceSingleWellRangeReportContentHandsontableHelper.hot != null && reportInstanceSingleWellRangeReportContentHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		reportInstanceSingleWellRangeReportContentHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	}]
                        	}]
                        }],
                        listeners: {
                        	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				oldCard.setIconCls(null);
                				newCard.setIconCls('check3');
                			},
                			tabchange: function (tabPanel, newCard, oldCard, obj) {
                            	var reportType=0;
                            	var selectedUnitId='';
                            	var selectedTemplateCode='';
                            	var selectedInstanceName='';
                            	if(newCard.id=="ModbusProtocolReportInstanceSingleWellDailyReportTemplatePanel_Id"){
                            		reportType=2;
                            	}else if(newCard.id=="ModbusProtocolReportInstanceSingleWellRangeReportTemplatePanel_Id"){
                            		reportType=0;
                            	}
                            	var instanceTreeSelection= Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getSelectionModel().getSelection();
                				var selectedUnitId=0;
                            	if(instanceTreeSelection.length>0){
                					var record=instanceTreeSelection[0];
                					if(record.data.classes==0){
                                		if(isNotVal(record.data.children) && record.data.children.length>0){
                                			selectedUnitId=record.data.children[0].unitId;
                                			selectedInstanceName=record.data.children[0].text;
                                			if(reportType==0){
                                				selectedTemplateCode=record.data.children[0].singleWellRangeReportTemplate;
                                			}else if(reportType==2){
                                				selectedTemplateCode=record.data.children[0].singleWellDailyReportTemplate;
                                			}
                                		}
                                	}else if(record.data.classes==2){//选中报表单元
                                		selectedUnitId=record.data.unitId;
                                		selectedInstanceName=record.parentNode.data.text;
                                		if(reportType==0){
                            				selectedTemplateCode=record.data.singleWellRangeReportTemplate;
                            			}else if(reportType==2){
                            				selectedTemplateCode=record.data.singleWellDailyReportTemplate;
                            			}
                                	}else{
                                		selectedUnitId=record.data.unitId;
                                		selectedInstanceName=record.data.text;
                                		if(reportType==0){
                            				selectedTemplateCode=record.data.singleWellRangeReportTemplate;
                            			}else if(reportType==2){
                            				selectedTemplateCode=record.data.singleWellDailyReportTemplate;
                            			}
                                	}
                                	
                                	if(reportType==0){
                            			CreateReportInstanceSingleWellRangeReportTemplateInfoTable(record.data.calculateType,selectedTemplateCode,selectedInstanceName);
                            			CreateSingleWellRangeReportInstanceTotalItemsInfoTable(record.data.calculateType,selectedUnitId,selectedInstanceName);
                                	}else if(reportType==2){
                            			CreateReportInstanceSingleWellDailyReportTemplateInfoTable(record.data.calculateType,selectedTemplateCode,selectedInstanceName);
                            			CreateSingleWellDailyReportInstanceTotalItemsInfoTable(record.data.calculateType,selectedUnitId,selectedInstanceName);
                                	}
                				}
                            }
                        }
                    },{
                    	title:loginUserLanguageResource.areaReport,
                    	id:'ModbusProtocolReportInstanceProductionReportTemplatePanel_Id',
                    	xtype: 'tabpanel',
                    	activeTab: 0,
                        border: false,
                        tabPosition: 'top',
                        items: [{
                        	id:'ModbusProtocolReportInstanceProductionRangeReportTemplatePanel_Id',
                        	title:loginUserLanguageResource.dailyReport,
                        	iconCls: 'check3',
                        	border: false,
                    		layout: "border",
                    		items: [{
                        		region: 'center',
                        		title:loginUserLanguageResource.areaDailyReportTemplate,
                        		id:"ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ModbusProtocolReportInstanceProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstanceProductionTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (reportInstanceProductionTemplateHandsontableHelper != null && reportInstanceProductionTemplateHandsontableHelper.hot != null && reportInstanceProductionTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		reportInstanceProductionTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'区域日报内容',
                            	border: false,
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	id:"ProductionReportInstanceContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ProductionReportInstanceContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProductionReportInstanceContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (reportInstanceProductionTemplateContentHandsontableHelper != null && reportInstanceProductionTemplateContentHandsontableHelper.hot != null && reportInstanceProductionTemplateContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		reportInstanceProductionTemplateContentHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}]
                        }],
                        listeners: {
                        	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				oldCard.setIconCls(null);
                				newCard.setIconCls('check3');
                			},
                			tabchange: function (tabPanel, newCard, oldCard, obj) {
                        		
                        	}
                        }
                    }],
                    listeners: {
                    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				oldCard.setIconCls(null);
            				newCard.setIconCls('check3');
            			},
            			tabchange: function (tabPanel, newCard, oldCard, obj) {
                        	var reportType=0;
                        	var selectedUnitId='';
                        	var selectedTemplateCode='';
                        	var selectedInstanceName='';
                        	if(newCard.id=="ModbusProtocolReportInstanceSingleWellReportTemplatePanel_Id"){
            					var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportInstanceSingleWellReportTemplatePanel_Id").getActiveTab().id;
            					if(singleWellReportActiveId=='ModbusProtocolReportInstanceSingleWellDailyReportTemplatePanel_Id'){
            						reportType=2;
            					}else if(singleWellReportActiveId=='ModbusProtocolReportInstanceSingleWellRangeReportTemplatePanel_Id'){
            						reportType=0;
            					}
            				}else if(newCard.id=="ModbusProtocolReportInstanceProductionReportTemplatePanel_Id"){
                        		reportType=1;
                        	}
                        	var instanceTreeSelection= Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getSelectionModel().getSelection();
            				var selectedUnitId=0;
                        	if(instanceTreeSelection.length>0){
            					var record=instanceTreeSelection[0];
            					if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			selectedUnitId=record.data.children[0].unitId;
                            			selectedInstanceName=record.data.children[0].text;
                            			if(reportType==0){
                            				selectedTemplateCode=record.data.children[0].singleWellRangeReportTemplate;
                            			}else if(reportType==2){
                            				selectedTemplateCode=record.data.children[0].singleWellDailyReportTemplate;
                            			}else{
                            				selectedTemplateCode=record.data.children[0].productionReportTemplate;
                            			}
                            		}
                            	}else if(record.data.classes==2){//选中报表单元
                            		selectedUnitId=record.data.unitId;
                            		selectedInstanceName=record.parentNode.data.text;
                            		if(reportType==0){
                        				selectedTemplateCode=record.data.singleWellRangeReportTemplate;
                        			}else if(reportType==2){
                        				selectedTemplateCode=record.data.singleWellDailyReportTemplate;
                        			}else{
                        				selectedTemplateCode=record.data.productionReportTemplate;
                        			}
                            	}else{
                            		selectedUnitId=record.data.unitId;
                            		selectedInstanceName=record.data.text;
                            		if(reportType==0){
                        				selectedTemplateCode=record.data.singleWellRangeReportTemplate;
                        			}else if(reportType==2){
                        				selectedTemplateCode=record.data.singleWellDailyReportTemplate;
                        			}else{
                        				selectedTemplateCode=record.data.productionReportTemplate;
                        			}
                            	}
                            	
                            	if(reportType==0){
                        			CreateReportInstanceSingleWellRangeReportTemplateInfoTable(record.data.calculateType,selectedTemplateCode,selectedInstanceName);
                        			CreateSingleWellRangeReportInstanceTotalItemsInfoTable(record.data.calculateType,selectedUnitId,selectedInstanceName);
                            	}else if(reportType==2){
                        			CreateReportInstanceSingleWellDailyReportTemplateInfoTable(record.data.calculateType,selectedTemplateCode,selectedInstanceName);
                        			CreateSingleWellDailyReportInstanceTotalItemsInfoTable(record.data.calculateType,selectedUnitId,selectedInstanceName);
                            	}else{
                            		CreateReportInstanceProductionTemplateInfoTable(record.data.calculateType,selectedTemplateCode,selectedInstanceName);
                            		CreateProductionReportInstanceTotalItemsInfoTable(record.data.calculateType,selectedUnitId,selectedInstanceName);
                            	}
            				}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateReportInstanceSingleWellRangeReportTemplateInfoTable(calculateType,code,selectedInstanceName){
	Ext.getCmp("ReportInstanceSingleWellRangeReportTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ReportInstanceSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ReportInstanceSingleWellRangeReportTemplateTableInfoPanel_Id").setTitle(selectedInstanceName+'/'+loginUserLanguageResource.deviceDailyReportTemplate);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(reportInstanceSingleWellRangeReportTemplateHandsontableHelper!=null){
				if(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot!=undefined){
					reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot.destroy();
				}
				reportInstanceSingleWellRangeReportTemplateHandsontableHelper=null;
			}
			
			if(reportInstanceSingleWellRangeReportTemplateHandsontableHelper==null || reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot==undefined){
				reportInstanceSingleWellRangeReportTemplateHandsontableHelper = ReportInstanceSingleWellRangeReportTemplateHandsontableHelper.createNew("ReportInstanceSingleWellRangeReportTemplateTableInfoDiv_id","ReportInstanceSingleWellRangeReportTemplateTableInfoContainer",result);
				reportInstanceSingleWellRangeReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ReportInstanceSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			reportType:0,
			calculateType:calculateType,
			code:code
        }
	});
}

var ReportInstanceSingleWellRangeReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var reportInstanceSingleWellRangeReportTemplateHandsontableHelper = {};
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData=templateData;
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.get_data = {};
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.data=[];
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot = '';
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.initData=function(){
	        	reportInstanceSingleWellRangeReportTemplateHandsontableHelper.data=[];
	        	if(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header!=undefined){
	        		for(var i=0;i<reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header.length;i++){
			        	reportInstanceSingleWellRangeReportTemplateHandsontableHelper.data.push(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].title);
			        }
	        	}
	        }
	        
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(reportInstanceSingleWellRangeReportTemplateHandsontableHelper!=null && reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.createTable = function () {
	            reportInstanceSingleWellRangeReportTemplateHandsontableHelper.container.innerHTML = "";
	            reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot = new Handsontable(reportInstanceSingleWellRangeReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: reportInstanceSingleWellRangeReportTemplateHandsontableHelper.data,
	                fixedRowsTop:reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.columnWidths,
//					rowHeaders: true, //显示行头
					rowHeaders(index) {
					    return 'Row ' + (index + 1);
					},
//					colHeaders: true, //显示列头
					colHeaders(index) {
					    return 'Col ' + (index + 1);
					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: reportInstanceSingleWellRangeReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = reportInstanceSingleWellRangeReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportInstanceSingleWellRangeReportTemplateHandsontableHelper!=null
	                		&& reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot!=''
	                		&& reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot!=undefined 
	                		&& reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportInstanceSingleWellRangeReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        reportInstanceSingleWellRangeReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	reportInstanceSingleWellRangeReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return reportInstanceSingleWellRangeReportTemplateHandsontableHelper;
	    }
	};

function CreateSingleWellRangeReportInstanceTotalItemsInfoTable(calculateType,selectedUnitId,selectedInstanceName){
	Ext.getCmp("ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportInstanceTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(selectedInstanceName)){
				Ext.getCmp("ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle(selectedInstanceName+'/单井日报表内容');
			}else{
				Ext.getCmp("ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle('单井日报表内容');
			}
			if(reportInstanceSingleWellRangeReportContentHandsontableHelper==null || reportInstanceSingleWellRangeReportContentHandsontableHelper.hot==undefined){
				reportInstanceSingleWellRangeReportContentHandsontableHelper = ReportInstanceSingleWellRangeReportContentHandsontableHelper.createNew("ReportInstanceSingleWellRangeReportContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','"+loginUserLanguageResource.reportCurve+"','','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType' }," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellRangeReportContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellRangeReportContentHandsontableHelper);}}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellRangeReportContentHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				reportInstanceSingleWellRangeReportContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportInstanceSingleWellRangeReportContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportInstanceSingleWellRangeReportContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportInstanceSingleWellRangeReportContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			calculateType:calculateType,
			unitId:selectedUnitId,
			reportType:0
        }
	});
};

var ReportInstanceSingleWellRangeReportContentHandsontableHelper = {
		createNew: function (divid) {
	        var reportInstanceSingleWellRangeReportContentHandsontableHelper = {};
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.hot1 = '';
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.divid = divid;
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.validresult=true;//数据校验
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.colHeaders=[];
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.columns=[];
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.AllData=[];
	        
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportInstanceSingleWellRangeReportContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportInstanceSingleWellRangeReportContentHandsontableHelper.divid);
	        	reportInstanceSingleWellRangeReportContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10,11],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,60,60,85,85,85],
	                columns:reportInstanceSingleWellRangeReportContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportInstanceSingleWellRangeReportContentHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(visualColIndex==8){
		                	cellProperties.renderer = reportInstanceSingleWellRangeReportContentHandsontableHelper.addCurveBg;
		                }else{
		                	cellProperties.renderer = reportInstanceSingleWellRangeReportContentHandsontableHelper.addCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportInstanceSingleWellRangeReportContentHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& reportInstanceSingleWellRangeReportContentHandsontableHelper!=null
	                		&& reportInstanceSingleWellRangeReportContentHandsontableHelper.hot!=''
	                		&& reportInstanceSingleWellRangeReportContentHandsontableHelper.hot!=undefined 
	                		&& reportInstanceSingleWellRangeReportContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportInstanceSingleWellRangeReportContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.saveData = function () {}
	        reportInstanceSingleWellRangeReportContentHandsontableHelper.clearContainer = function () {
	        	reportInstanceSingleWellRangeReportContentHandsontableHelper.AllData = [];
	        }
	        return reportInstanceSingleWellRangeReportContentHandsontableHelper;
	    }
};

function CreateReportInstanceSingleWellDailyReportTemplateInfoTable(calculateType,code,selectedInstanceName){
	Ext.getCmp("ReportInstanceSingleWellDailyReportTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ReportInstanceSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ReportInstanceSingleWellDailyReportTemplateTableInfoPanel_Id").setTitle(selectedInstanceName+'/'+loginUserLanguageResource.deviceHourlyReportTemplate);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(reportInstanceSingleWellDailyReportTemplateHandsontableHelper!=null){
				if(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot!=undefined){
					reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot.destroy();
				}
				reportInstanceSingleWellDailyReportTemplateHandsontableHelper=null;
			}
			
			if(reportInstanceSingleWellDailyReportTemplateHandsontableHelper==null || reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot==undefined){
				reportInstanceSingleWellDailyReportTemplateHandsontableHelper = ReportInstanceSingleWellDailyReportTemplateHandsontableHelper.createNew("ReportInstanceSingleWellDailyReportTemplateTableInfoDiv_id","ReportInstanceSingleWellDailyReportTemplateTableInfoContainer",result);
				reportInstanceSingleWellDailyReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ReportInstanceSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			reportType:2,
			calculateType:calculateType,
			code:code
        }
	});
}

var ReportInstanceSingleWellDailyReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var reportInstanceSingleWellDailyReportTemplateHandsontableHelper = {};
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData=templateData;
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.get_data = {};
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.data=[];
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot = '';
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.initData=function(){
	        	reportInstanceSingleWellDailyReportTemplateHandsontableHelper.data=[];
	        	if(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header!=undefined){
	        		for(var i=0;i<reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header.length;i++){
			        	reportInstanceSingleWellDailyReportTemplateHandsontableHelper.data.push(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].title);
			        }
	        	}
	        }
	        
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(reportInstanceSingleWellDailyReportTemplateHandsontableHelper!=null && reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.createTable = function () {
	            reportInstanceSingleWellDailyReportTemplateHandsontableHelper.container.innerHTML = "";
	            reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot = new Handsontable(reportInstanceSingleWellDailyReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: reportInstanceSingleWellDailyReportTemplateHandsontableHelper.data,
	                fixedRowsTop:reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.columnWidths,
//					rowHeaders: true, //显示行头
					rowHeaders(index) {
					    return 'Row ' + (index + 1);
					},
//					colHeaders: true, //显示列头
					colHeaders(index) {
					    return 'Col ' + (index + 1);
					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: reportInstanceSingleWellDailyReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = reportInstanceSingleWellDailyReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportInstanceSingleWellDailyReportTemplateHandsontableHelper!=null
	                		&& reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot!=''
	                		&& reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot!=undefined 
	                		&& reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportInstanceSingleWellDailyReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        reportInstanceSingleWellDailyReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	reportInstanceSingleWellDailyReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return reportInstanceSingleWellDailyReportTemplateHandsontableHelper;
	    }
	};

function CreateSingleWellDailyReportInstanceTotalItemsInfoTable(calculateType,selectedUnitId,selectedInstanceName){
	Ext.getCmp("ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportInstanceTimingTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(selectedInstanceName)){
				Ext.getCmp("ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(selectedInstanceName+'/单井班报表内容');
			}else{
				Ext.getCmp("ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle('单井班报表内容');
			}
			if(reportInstanceSingleWellDailyReportContentHandsontableHelper==null || reportInstanceSingleWellDailyReportContentHandsontableHelper.hot==undefined){
				reportInstanceSingleWellDailyReportContentHandsontableHelper = ReportInstanceSingleWellDailyReportContentHandsontableHelper.createNew("ReportInstanceSingleWellDailyReportContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','"+loginUserLanguageResource.reportCurve+"','','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType'}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellDailyReportContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellDailyReportContentHandsontableHelper);}}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellDailyReportContentHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				reportInstanceSingleWellDailyReportContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportInstanceSingleWellDailyReportContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportInstanceSingleWellDailyReportContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportInstanceSingleWellDailyReportContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			calculateType:calculateType,
			unitId:selectedUnitId,
			reportType:2
        }
	});
};

var ReportInstanceSingleWellDailyReportContentHandsontableHelper = {
		createNew: function (divid) {
	        var reportInstanceSingleWellDailyReportContentHandsontableHelper = {};
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.hot1 = '';
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.divid = divid;
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.validresult=true;//数据校验
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.colHeaders=[];
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.columns=[];
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.AllData=[];
	        
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportInstanceSingleWellDailyReportContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportInstanceSingleWellDailyReportContentHandsontableHelper.divid);
	        	reportInstanceSingleWellDailyReportContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10,11],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,60,60,85,85,85],
	                columns:reportInstanceSingleWellDailyReportContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportInstanceSingleWellDailyReportContentHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(visualColIndex==8){
		                	cellProperties.renderer = reportInstanceSingleWellDailyReportContentHandsontableHelper.addCurveBg;
		                }else{
		                	if(reportInstanceSingleWellDailyReportContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& reportInstanceSingleWellDailyReportContentHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = reportInstanceSingleWellDailyReportContentHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportInstanceSingleWellDailyReportContentHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& reportInstanceSingleWellDailyReportContentHandsontableHelper!=null
	                		&& reportInstanceSingleWellDailyReportContentHandsontableHelper.hot!=''
	                		&& reportInstanceSingleWellDailyReportContentHandsontableHelper.hot!=undefined 
	                		&& reportInstanceSingleWellDailyReportContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportInstanceSingleWellDailyReportContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.saveData = function () {}
	        reportInstanceSingleWellDailyReportContentHandsontableHelper.clearContainer = function () {
	        	reportInstanceSingleWellDailyReportContentHandsontableHelper.AllData = [];
	        }
	        return reportInstanceSingleWellDailyReportContentHandsontableHelper;
	    }
};

function CreateProductionReportInstanceTotalItemsInfoTable(calculateType,selectedUnitId,selectedInstanceName){
	Ext.getCmp("ProductionReportInstanceContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportInstanceTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ProductionReportInstanceContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(selectedInstanceName)){
				Ext.getCmp("ProductionReportInstanceContentConfigTableInfoPanel_Id").setTitle(selectedInstanceName+'/区域日报内容');
			}else{
				Ext.getCmp("ProductionReportInstanceContentConfigTableInfoPanel_Id").setTitle('区域日报内容');
			}
			if(reportInstanceProductionTemplateContentHandsontableHelper==null || reportInstanceProductionTemplateContentHandsontableHelper.hot==undefined){
				reportInstanceProductionTemplateContentHandsontableHelper = ReportInstanceProductionTemplateContentHandsontableHelper.createNew("ProductionReportInstanceContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','求和','求平均','"+loginUserLanguageResource.reportCurve+"','曲线统计类型','','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType'}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'sumSign',type:'checkbox'}," 
						+"{data:'averageSign',type:'checkbox'}," 
						
						+"{data:'reportCurveConfShowValue'},"
						
						+"{data:'curveStatType',type:'dropdown',strict:true,allowInvalid:false,source:['合计', '平均']},"
						
						+"{data:'reportCurveConf'},"
						
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				reportInstanceProductionTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportInstanceProductionTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportInstanceProductionTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportInstanceProductionTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ProductionReportInstanceContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			calculateType:calculateType,
			unitId:selectedUnitId,
			reportType:1
        }
	});
};

var ReportInstanceProductionTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var reportInstanceProductionTemplateContentHandsontableHelper = {};
	        reportInstanceProductionTemplateContentHandsontableHelper.hot1 = '';
	        reportInstanceProductionTemplateContentHandsontableHelper.divid = divid;
	        reportInstanceProductionTemplateContentHandsontableHelper.validresult=true;//数据校验
	        reportInstanceProductionTemplateContentHandsontableHelper.colHeaders=[];
	        reportInstanceProductionTemplateContentHandsontableHelper.columns=[];
	        reportInstanceProductionTemplateContentHandsontableHelper.AllData=[];
	        
	        reportInstanceProductionTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportInstanceProductionTemplateContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportInstanceProductionTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportInstanceProductionTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportInstanceProductionTemplateContentHandsontableHelper.divid);
	        	reportInstanceProductionTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [12,13,14],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,60,60,60,30,45,85,85,70],
	                columns:reportInstanceProductionTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportInstanceProductionTemplateContentHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(visualColIndex==10){
		                	cellProperties.renderer = reportInstanceProductionTemplateContentHandsontableHelper.addCurveBg;
		                }else{
		                	if(reportInstanceProductionTemplateContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& reportInstanceProductionTemplateContentHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = reportInstanceProductionTemplateContentHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportInstanceProductionTemplateContentHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& reportInstanceProductionTemplateContentHandsontableHelper!=null
	                		&& reportInstanceProductionTemplateContentHandsontableHelper.hot!=''
	                		&& reportInstanceProductionTemplateContentHandsontableHelper.hot!=undefined 
	                		&& reportInstanceProductionTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportInstanceProductionTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        reportInstanceProductionTemplateContentHandsontableHelper.saveData = function () {}
	        reportInstanceProductionTemplateContentHandsontableHelper.clearContainer = function () {
	        	reportInstanceProductionTemplateContentHandsontableHelper.AllData = [];
	        }
	        return reportInstanceProductionTemplateContentHandsontableHelper;
	    }
};

function SaveReportInstanceData(){
	var InstanceTreeSelectRow= Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").getValue();
	if(InstanceTreeSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getStore().getAt(InstanceTreeSelectRow);
		var propertiesData=protocolReportInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.name=propertiesData[0][2];
			saveData.unitName=propertiesData[1][2];
			saveData.sort=propertiesData[2][2];
			SaveModbusProtocolReportInstanceData(saveData);
		}
	}
};



function SaveModbusProtocolReportInstanceData(saveData){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolReportInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getStore().load();
            	
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			data: JSON.stringify(saveData),
        }
	});
}

function CreateProtocolReportInstancePropertiesInfoTable(data){
	var root=[];
	var unitList=[];
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			unitList=result.unitList;
			
			if(data.classes==0){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.rootNode;
				item1.value='实例列表';
				root.push(item1);
			}else if(data.classes==1){
				var item1={};
				item1.id=1;
				item1.title='实例名称';
				item1.value=data.text;
				root.push(item1);
				
				var item2={};
				item2.id=2;
				item2.title=loginUserLanguageResource.reportUnit;
				item2.value=data.unitName;
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title=loginUserLanguageResource.sortNum;
				item3.value=data.sort;
				root.push(item3);
			}else if(data.classes==2){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.reportUnit;
				item1.value=data.text;
				root.push(item1);
			}
			
			if(protocolReportInstancePropertiesHandsontableHelper==null || protocolReportInstancePropertiesHandsontableHelper.hot==undefined){
				protocolReportInstancePropertiesHandsontableHelper = ProtocolReportInstancePropertiesHandsontableHelper.createNew("ModbusProtocolReportInstancePropertiesTableInfoDiv_id");
				var colHeaders="['序号','名称','变量']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolReportInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolReportInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolReportInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolReportInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolReportInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolReportInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			
        }
	});
};

var ProtocolReportInstancePropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolReportInstancePropertiesHandsontableHelper = {};
	        protocolReportInstancePropertiesHandsontableHelper.hot = '';
	        protocolReportInstancePropertiesHandsontableHelper.classes =null;
	        protocolReportInstancePropertiesHandsontableHelper.divid = divid;
	        protocolReportInstancePropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolReportInstancePropertiesHandsontableHelper.colHeaders=[];
	        protocolReportInstancePropertiesHandsontableHelper.columns=[];
	        protocolReportInstancePropertiesHandsontableHelper.AllData=[];
	        protocolReportInstancePropertiesHandsontableHelper.unitList=[];
	        
	        protocolReportInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolReportInstancePropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolReportInstancePropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolReportInstancePropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolReportInstancePropertiesHandsontableHelper.divid);
	        	protocolReportInstancePropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,4,7],
	                columns:protocolReportInstancePropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolReportInstancePropertiesHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
		                    if(protocolReportInstancePropertiesHandsontableHelper.classes===0 || protocolReportInstancePropertiesHandsontableHelper.classes===2){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolReportInstancePropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolReportInstancePropertiesHandsontableHelper);
			                    	}
			                    }else if(visualColIndex === 2 && visualRowIndex===2){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolReportInstancePropertiesHandsontableHelper);
			                    	}
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
			                    	var calculateType='';
			                    	if(isNotVal(protocolReportInstancePropertiesHandsontableHelper.hot)){
			                    		calculateType=protocolReportInstancePropertiesHandsontableHelper.hot.getDataAtCell(1,2);
			                    	}
			                    	
		                    		this.type = 'dropdown';
		                    		this.source = protocolReportInstancePropertiesHandsontableHelper.unitList;
			                    	
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }
		                    	
		                    	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
				                }else if(visualColIndex === 2 && visualRowIndex!=1){
				                	cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addCellStyle;
				                }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolReportInstancePropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolReportInstancePropertiesHandsontableHelper!=null
	                		&& protocolReportInstancePropertiesHandsontableHelper.hot!=''
	                		&& protocolReportInstancePropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolReportInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolReportInstancePropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        protocolReportInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolReportInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolReportInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolReportInstancePropertiesHandsontableHelper;
	    }
};

function CreateReportInstanceProductionTemplateInfoTable(calculateType,code,selectedInstanceName){
	Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").setTitle(selectedInstanceName+'/'+loginUserLanguageResource.areaDailyReportTemplate);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(reportInstanceProductionTemplateHandsontableHelper!=null){
				if(reportInstanceProductionTemplateHandsontableHelper.hot!=undefined){
					reportInstanceProductionTemplateHandsontableHelper.hot.destroy();
				}
				reportInstanceProductionTemplateHandsontableHelper=null;
			}
			if(reportInstanceProductionTemplateHandsontableHelper==null || reportInstanceProductionTemplateHandsontableHelper.hot==undefined){
				reportInstanceProductionTemplateHandsontableHelper = ReportInstanceProductionTemplateHandsontableHelper.createNew("ModbusProtocolReportInstanceProductionTemplateTableInfoDiv_id","ModbusProtocolReportInstanceProductionTemplateTableInfoContainer",result);
				reportInstanceProductionTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			reportType:1,
			calculateType:calculateType,
			code:code
        }
	});
}

var ReportInstanceProductionTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var reportInstanceProductionTemplateHandsontableHelper = {};
	        reportInstanceProductionTemplateHandsontableHelper.templateData=templateData;
	        reportInstanceProductionTemplateHandsontableHelper.get_data = {};
	        reportInstanceProductionTemplateHandsontableHelper.data=[];
	        reportInstanceProductionTemplateHandsontableHelper.hot = '';
	        reportInstanceProductionTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        reportInstanceProductionTemplateHandsontableHelper.initData=function(){
	        	reportInstanceProductionTemplateHandsontableHelper.data=[];
	        	if(reportInstanceProductionTemplateHandsontableHelper.templateData.header!=undefined){
	        		for(var i=0;i<reportInstanceProductionTemplateHandsontableHelper.templateData.header.length;i++){
			        	reportInstanceProductionTemplateHandsontableHelper.data.push(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].title);
			        }
	        	}
	        }
	        
	        reportInstanceProductionTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(reportInstanceProductionTemplateHandsontableHelper!=null && reportInstanceProductionTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<reportInstanceProductionTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        reportInstanceProductionTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			reportInstanceProductionTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			reportInstanceProductionTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        reportInstanceProductionTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        reportInstanceProductionTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        reportInstanceProductionTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        reportInstanceProductionTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        reportInstanceProductionTemplateHandsontableHelper.createTable = function () {
	            reportInstanceProductionTemplateHandsontableHelper.container.innerHTML = "";
	            reportInstanceProductionTemplateHandsontableHelper.hot = new Handsontable(reportInstanceProductionTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: reportInstanceProductionTemplateHandsontableHelper.data,
	                fixedRowsTop:reportInstanceProductionTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: reportInstanceProductionTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: reportInstanceProductionTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: reportInstanceProductionTemplateHandsontableHelper.templateData.columnWidths,
//					rowHeaders: true, //显示行头
					rowHeaders(index) {
					    return 'Row ' + (index + 1);
					},
//					colHeaders: true, //显示列头
					colHeaders(index) {
					    return 'Col ' + (index + 1);
					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: reportInstanceProductionTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = reportInstanceProductionTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportInstanceProductionTemplateHandsontableHelper!=null
	                		&& reportInstanceProductionTemplateHandsontableHelper.hot!=''
	                		&& reportInstanceProductionTemplateHandsontableHelper.hot!=undefined 
	                		&& reportInstanceProductionTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportInstanceProductionTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        reportInstanceProductionTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	reportInstanceProductionTemplateHandsontableHelper.initData();
	        }

	        init();
	        return reportInstanceProductionTemplateHandsontableHelper;
	    }
	};