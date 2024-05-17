var singleWellRangeReportTemplateHandsontableHelper=null;
var singleWellRangeReportTemplateContentHandsontableHelper=null;
var reportUnitPropertiesHandsontableHelper=null;

var productionReportTemplateHandsontableHelper=null;
var productionReportTemplateContentHandsontableHelper=null;

var singleWellDailyReportTemplateHandsontableHelper=null;
var singleWellDailyReportTemplateContentHandsontableHelper=null;

Ext.define('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolReportUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolReportUnitConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加报表单元',
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addReportUnitInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveReportUnitData();
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'16%',
                    layout: "border",
                    border: false,
                    header: false,
                    split: true,
                    collapsible: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'报表单元配置',
                    	border: false,
                    	layout: 'fit',
                    	id:"ModbusProtocolReportUnitConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                    	border: false,
                        split: true,
                        collapsible: true,
                        hidden: false,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolReportUnitPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if (reportUnitPropertiesHandsontableHelper != null && reportUnitPropertiesHandsontableHelper.hot != null && reportUnitPropertiesHandsontableHelper.hot != undefined) {
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		reportUnitPropertiesHandsontableHelper.hot.updateSettings({
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
                	id:"ModbusProtocolReportUnitReportTemplateTabPanel_Id",
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	title:'单井报表',
                    	id:'ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id',
                    	xtype: 'tabpanel',
                    	activeTab: 0,
                        border: false,
                        tabPosition: 'top',
                        items: [{
                        	title:'班报表',
                        	id:'ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id',
                        	layout: "border",
                        	border: false,
                        	items: [{
                        		region: 'west',
                            	width:'16%',
                            	layout: 'fit',
                                border: false,
                                id:'ReportUnitSingleWellDailyReportTemplateListPanel_Id',
                                title:'报表模板列表',
                                header: false,
                                split: true,
                                collapsible: true
                        	},{
                        		region: 'center',
                        		layout: "border",
                        		border: false,
                        		items: [{
                            		region: 'center',
                            		title:'单井班报表模板',
                            		id:"ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id",
                                    layout: 'fit',
                                    border: false,
                                    html:'<div class="ReportUnitSingleWellDailyReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellDailyReportTemplateTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (singleWellDailyReportTemplateHandsontableHelper != null && singleWellDailyReportTemplateHandsontableHelper.hot != null && singleWellDailyReportTemplateHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		singleWellDailyReportTemplateHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	},{
                            		region: 'south',
                                	height:'50%',
                                	title:'单井班报表内容配置',
                                	collapsible: true,
                                    split: true,
                                	layout: 'fit',
                                	border: false,
                                	id:"ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id",
                                    layout: 'fit',
                                    html:'<div class="ReportUnitSingleWellDailyReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (singleWellDailyReportTemplateContentHandsontableHelper != null && singleWellDailyReportTemplateContentHandsontableHelper.hot != null && singleWellDailyReportTemplateContentHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		singleWellDailyReportTemplateContentHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	}]
                        	}]
                        },{
                        	title:'日报表',
                        	id:'ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id',
                        	layout: "border",
                        	border: false,
                        	items: [{
                        		region: 'west',
                            	width:'16%',
                            	layout: 'fit',
                                border: false,
                                id:'ReportUnitSingleWellRangeReportTemplateListPanel_Id',
                                title:'报表模板列表',
                                header: false,
                                split: true,
                                collapsible: true
                        	},{
                        		region: 'center',
                        		layout: "border",
                        		border: false,
                        		items: [{
                            		region: 'center',
                            		title:'单井日报表模板',
                            		id:"ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id",
                                    layout: 'fit',
                                    border: false,
                                    html:'<div class="ReportUnitSingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (singleWellRangeReportTemplateHandsontableHelper != null && singleWellRangeReportTemplateHandsontableHelper.hot != null && singleWellRangeReportTemplateHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                            	singleWellRangeReportTemplateHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	},{
                            		region: 'south',
                                	height:'60%',
                                	title:'单井日报表内容配置',
                                	collapsible: true,
                                    split: true,
                                	layout: 'fit',
                                	border: false,
                                	id:"ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id",
                                    layout: 'fit',
                                    html:'<div class="ReportUnitSingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (singleWellRangeReportTemplateContentHandsontableHelper != null && singleWellRangeReportTemplateContentHandsontableHelper.hot != null && singleWellRangeReportTemplateContentHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		singleWellRangeReportTemplateContentHandsontableHelper.hot.updateSettings({
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
                            tabchange: function (tabPanel, newCard, oldCard, obj) {
                            	var unitTreeSelection= Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
                				var selectedUnitId=0;
                            	if(unitTreeSelection.length>0){
                					var record=unitTreeSelection[0];
                					if(record.data.classes==0 && isNotVal(record.data.children) && record.data.children.length>0){
                						selectedUnitId=record.data.children[0].id;
                					}else if(record.data.classes==1){
                                		selectedUnitCode=record.data.code;
                                		selectedUnitId=record.data.id;
                                	}
                				}
                            	
                            	if(newCard.id=="ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id"){
            						var ReportUnitSingleWellDailyReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitSingleWellDailyReportTemplateListGridPanel)) {
                                		ReportUnitSingleWellDailyReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore')
                                	}
                                	CreateSingleWellDailyReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
                                	
            					}else if(newCard.id=="ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id"){
                            		var ReportUnitSingleWellRangeReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitSingleWellRangeReportTemplateListGridPanel)) {
                                		ReportUnitSingleWellRangeReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore')
                                	}
//                                	CreateSingleWellRangeReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
                            	}
                            }
                        }
                    },{
                    	title:'区域报表',
                    	id:'ModbusProtocolReportUnitProductionReportTemplatePanel_Id',
                    	xtype: 'tabpanel',
                    	activeTab: 0,
                        border: false,
                        tabPosition: 'top',
                        items: [{
                        	title:'日报表',
                        	id:'ModbusProtocolReportUnitProductionRangeReportTemplatePanel_Id',
                        	layout: "border",
                        	border: false,
                        	items: [{
                        		region: 'west',
                            	width:'16%',
                            	layout: 'fit',
                                border: false,
                                id:'ReportUnitProductionReportTemplateListPanel_Id',
                                title:'报表模板列表',
                                header: false,
                                split: true,
                                collapsible: true
                        	},{
                        		region: 'center',
                        		layout: "border",
                        		border: false,
                        		items: [{
                            		region: 'center',
                            		title:'区间日报模板',
                            		id:"ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id",
                                    layout: 'fit',
                                    border: false,
                                    html:'<div class="ModbusProtocolReportUnitProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitProductionTemplateTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (productionReportTemplateHandsontableHelper != null && productionReportTemplateHandsontableHelper.hot != null && productionReportTemplateHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		productionReportTemplateHandsontableHelper.hot.updateSettings({
                                        			width:newWidth,
                                        			height:newHeight
                                        		});
                                            }
                                        }
                                    }
                            	},{
                            		region: 'south',
                                	height:'50%',
                                	title:'单井日报表内容配置',
                                	collapsible: true,
                                    split: true,
                                	layout: 'fit',
                                	border: false,
                                	id:"ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id",
                                    layout: 'fit',
                                    html:'<div class="ModbusProtocolProductionReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolProductionReportUnitContentConfigTableInfoDiv_id"></div></div>',
                                    listeners: {
                                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                        	if (productionReportTemplateContentHandsontableHelper != null && productionReportTemplateContentHandsontableHelper.hot != null && productionReportTemplateContentHandsontableHelper.hot != undefined) {
                                        		var newWidth=width;
                                        		var newHeight=height;
                                        		var header=thisPanel.getHeader();
                                        		if(header){
                                        			newHeight=newHeight-header.lastBox.height-2;
                                        		}
                                        		productionReportTemplateContentHandsontableHelper.hot.updateSettings({
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
                        	tabchange: function (tabPanel, newCard, oldCard, obj) {
                        		
                        	}
                        }
                    }],
                    listeners: {
                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                        	var unitTreeSelection= Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
            				var selectedUnitId=0;
                        	if(unitTreeSelection.length>0){
            					var record=unitTreeSelection[0];
            					if(record.data.classes==0 && isNotVal(record.data.children) && record.data.children.length>0){
            						selectedUnitId=record.data.children[0].id;
            					}else if(record.data.classes==1){
                            		selectedUnitCode=record.data.code;
                            		selectedUnitId=record.data.id;
                            	}
            				}
                        	
                        	if(newCard.id=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
                        		var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
            					if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id'){
            						var ReportUnitSingleWellDailyReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitSingleWellDailyReportTemplateListGridPanel)) {
                                		ReportUnitSingleWellDailyReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore')
                                	}
                                	CreateSingleWellDailyReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
                                	
            					}else if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id'){
            						var ReportUnitSingleWellRangeReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitSingleWellRangeReportTemplateListGridPanel)) {
                                		ReportUnitSingleWellRangeReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore')
                                	}
//                                	CreateSingleWellRangeReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
            					}
                        	}else if(newCard.id=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
                        		var ReportUnitProductionReportTemplateListGridPanel=Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id");
                            	if (isNotVal(ReportUnitProductionReportTemplateListGridPanel)) {
                            		ReportUnitProductionReportTemplateListGridPanel.getStore().load();
                            	}else{
                            		Ext.create('AP.store.acquisitionUnit.ModbusProtocolProductionReportTemplateStore')
                            	}
                            	CreateproductionReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
                        	}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateSingleWellRangeReportTemplateInfoTable(name,calculateType,code){
	Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").setTitle('单井日报表模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(singleWellRangeReportTemplateHandsontableHelper!=null){
				if(singleWellRangeReportTemplateHandsontableHelper.hot!=undefined){
					singleWellRangeReportTemplateHandsontableHelper.hot.destroy();
				}
				singleWellRangeReportTemplateHandsontableHelper=null;
			}
			
			if(singleWellRangeReportTemplateHandsontableHelper==null || singleWellRangeReportTemplateHandsontableHelper.hot==undefined){
				singleWellRangeReportTemplateHandsontableHelper = SingleWellRangeReportTemplateHandsontableHelper.createNew("ReportUnitSingleWellRangeReportTemplateTableInfoDiv_id","ReportUnitSingleWellRangeReportTemplateTableInfoContainer",result);
				singleWellRangeReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reportType:0,
			calculateType:calculateType,
			code:code
        }
	});
}

var SingleWellRangeReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var singleWellRangeReportTemplateHandsontableHelper = {};
	        singleWellRangeReportTemplateHandsontableHelper.templateData=templateData;
	        singleWellRangeReportTemplateHandsontableHelper.get_data = {};
	        singleWellRangeReportTemplateHandsontableHelper.data=[];
	        singleWellRangeReportTemplateHandsontableHelper.hot = '';
	        singleWellRangeReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        singleWellRangeReportTemplateHandsontableHelper.initData=function(){
	        	singleWellRangeReportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<singleWellRangeReportTemplateHandsontableHelper.templateData.header.length;i++){
		        	singleWellRangeReportTemplateHandsontableHelper.data.push(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        singleWellRangeReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(singleWellRangeReportTemplateHandsontableHelper!=null && singleWellRangeReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<singleWellRangeReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = singleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        singleWellRangeReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			singleWellRangeReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			singleWellRangeReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        singleWellRangeReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        singleWellRangeReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        singleWellRangeReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        singleWellRangeReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        singleWellRangeReportTemplateHandsontableHelper.createTable = function () {
	            singleWellRangeReportTemplateHandsontableHelper.container.innerHTML = "";
	            singleWellRangeReportTemplateHandsontableHelper.hot = new Handsontable(singleWellRangeReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: singleWellRangeReportTemplateHandsontableHelper.data,
	                fixedRowsTop:singleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: singleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: singleWellRangeReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: singleWellRangeReportTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: singleWellRangeReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = singleWellRangeReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        singleWellRangeReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	singleWellRangeReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return singleWellRangeReportTemplateHandsontableHelper;
	    }
	};

function CreateSingleWellDailyReportTemplateInfoTable(name,calculateType,code){
	Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").setTitle('单井班报表模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(singleWellDailyReportTemplateHandsontableHelper!=null){
				if(singleWellDailyReportTemplateHandsontableHelper.hot!=undefined){
					singleWellDailyReportTemplateHandsontableHelper.hot.destroy();
				}
				singleWellDailyReportTemplateHandsontableHelper=null;
			}
			
			if(singleWellDailyReportTemplateHandsontableHelper==null || singleWellDailyReportTemplateHandsontableHelper.hot==undefined){
				singleWellDailyReportTemplateHandsontableHelper = SingleWellDailyReportTemplateHandsontableHelper.createNew("ReportUnitSingleWellDailyReportTemplateTableInfoDiv_id","ReportUnitSingleWellDailyReportTemplateTableInfoContainer",result);
				singleWellDailyReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reportType:2,
			calculateType:calculateType,
			code:code
        }
	});
}

var SingleWellDailyReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var singleWellDailyReportTemplateHandsontableHelper = {};
	        singleWellDailyReportTemplateHandsontableHelper.templateData=templateData;
	        singleWellDailyReportTemplateHandsontableHelper.get_data = {};
	        singleWellDailyReportTemplateHandsontableHelper.data=[];
	        singleWellDailyReportTemplateHandsontableHelper.hot = '';
	        singleWellDailyReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        singleWellDailyReportTemplateHandsontableHelper.initData=function(){
	        	singleWellDailyReportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<singleWellDailyReportTemplateHandsontableHelper.templateData.header.length;i++){
		        	singleWellDailyReportTemplateHandsontableHelper.data.push(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        singleWellDailyReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(singleWellDailyReportTemplateHandsontableHelper!=null && singleWellDailyReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<singleWellDailyReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = singleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        singleWellDailyReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			singleWellDailyReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			singleWellDailyReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        singleWellDailyReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        singleWellDailyReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        singleWellDailyReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        singleWellDailyReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        singleWellDailyReportTemplateHandsontableHelper.createTable = function () {
	            singleWellDailyReportTemplateHandsontableHelper.container.innerHTML = "";
	            singleWellDailyReportTemplateHandsontableHelper.hot = new Handsontable(singleWellDailyReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: singleWellDailyReportTemplateHandsontableHelper.data,
	                fixedRowsTop:singleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: singleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: singleWellDailyReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: singleWellDailyReportTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: singleWellDailyReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = singleWellDailyReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        singleWellDailyReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	singleWellDailyReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return singleWellDailyReportTemplateHandsontableHelper;
	    }
	};

function renderReportUnitContentConfig(instance, td, row, col, prop, value, cellProperties) {
//	td.innerHTML = "<button onclick=alert('打开此列内容配置窗口') type='button'>配置</button>";
	td.innerHTML = "<a href='#' onclick=reportUnitContentConfig("+row+","+col+","+value+")><span>配置</span></a>";
}

function reportUnitContentConfig(row, col,value) {
	var reportType=getReportType();
	var calculateType=0;
	var unitId=0;
	var unitName='';
	var classes=0;
	var reportUnitConfigTreeSelection= Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
	if(reportUnitConfigTreeSelection.length>0){
		var record=reportUnitConfigTreeSelection[0];
		classes=record.data.classes;
		if(classes==0){
    		if(isNotVal(record.data.children) && record.data.children.length>0){
    			unitId=record.data.children[0].id;
    			unitName=record.data.children[0].text;
    		}
    	}else if(classes==1){
    		unitId=record.data.id;
    		unitName=record.data.text;
    	}
		calculateType=record.data.calculateType;
	}
	
	var window = Ext.create("AP.view.acquisitionUnit.ReportUnitContentConfigWindow", {
        title: '报表内容配置'
    });
	
	Ext.getCmp("ReportUnitContentConfig_UnitId").setValue(unitId);
    Ext.getCmp("ReportUnitContentConfig_ReportType").setValue(reportType);
    Ext.getCmp("ReportUnitContentConfig_CalculateType").setValue(calculateType);
    Ext.getCmp("ReportUnitContentConfig_SelectedRow").setValue(row);
    Ext.getCmp("ReportUnitContentConfig_SelectedCol").setValue(col);
	
    window.show();
    
}

function CreateSingleWellRangeReportTotalItemsInfoTable(){
	Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	
	var calculateType=0;
	var unitId=0;
	var unitName='';
	var classes=0;
	var reportUnitConfigTreeSelection= Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getSelectionModel().getSelection();
	if(reportUnitConfigTreeSelection.length>0){
		var record=reportUnitConfigTreeSelection[0];
		if(record.data.classes==0){
    		if(isNotVal(record.data.children) && record.data.children.length>0){
    			unitId=record.data.children[0].id;
    		}
    	}else if(record.data.classes==1){
    		unitId=record.data.id;
    	}
		calculateType=record.data.calculateType;
		unitName=record.data.text;
		classes=record.data.classes;
	}
	
	var templateSelection= Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
	var templateCode="";
	if(templateSelection.length>0){
		templateCode=templateSelection[0].data.templateCode;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==0){
				Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle('单井日报表内容配置');
			}else{
				Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle(unitName+'/单井日报表内容配置');
			}
			if(singleWellRangeReportTemplateContentHandsontableHelper==null || singleWellRangeReportTemplateContentHandsontableHelper.hot==undefined){
				singleWellRangeReportTemplateContentHandsontableHelper = SingleWellRangeReportTemplateContentHandsontableHelper.createNew("ReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id");
				var colHeaders="['列','表头','项','单位','数据来源','统计方式','显示级别','小数位数','报表曲线','配置']";
				var columns=[
						{data:'id'},
						{data:'headerName'},
						{data:'itemName'},
					 	{data:'unit'},
					 	{data:'dataSource'},
					 	{data:'totalType'},
						{data:'showLevel'},
						{data:'prec'},
						{data:'reportCurveConfShowValue'},
						{data:'Delete', renderer:renderReportUnitContentConfig}
						];
				singleWellRangeReportTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
//				singleWellRangeReportTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				singleWellRangeReportTemplateContentHandsontableHelper.columns=columns;
				singleWellRangeReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				singleWellRangeReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			calculateType: calculateType,
			reportType: 0,
			unitId: unitId,
			templateCode: templateCode,
			classes: classes
        }
	});
};

var SingleWellRangeReportTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var singleWellRangeReportTemplateContentHandsontableHelper = {};
	        singleWellRangeReportTemplateContentHandsontableHelper.hot1 = '';
	        singleWellRangeReportTemplateContentHandsontableHelper.divid = divid;
	        singleWellRangeReportTemplateContentHandsontableHelper.validresult=true;//数据校验
	        singleWellRangeReportTemplateContentHandsontableHelper.colHeaders=[];
	        singleWellRangeReportTemplateContentHandsontableHelper.columns=[];
	        singleWellRangeReportTemplateContentHandsontableHelper.AllData=[];
	        
	        singleWellRangeReportTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        singleWellRangeReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        singleWellRangeReportTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+singleWellRangeReportTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+singleWellRangeReportTemplateContentHandsontableHelper.divid);
	        	singleWellRangeReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [50,150,150,80,60,60,60,60,85,80],
	                columns:singleWellRangeReportTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:singleWellRangeReportTemplateContentHandsontableHelper.colHeaders,//显示列头
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
	                    
//	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
//	                    if(protocolConfigModuleEditFlag==1){
//	                    	if(visualColIndex<=8){
//	                    		cellProperties.readOnly = true;
//	                    	}
//	                    }else{
//	                    	cellProperties.readOnly = true;
//	                    }
	                    
	                    
	                    
	                    if(visualColIndex==8){
		                	cellProperties.renderer = singleWellRangeReportTemplateContentHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(singleWellRangeReportTemplateContentHandsontableHelper!=null && singleWellRangeReportTemplateContentHandsontableHelper.hot!=undefined){
		                	if(column==9){
		                		alert("配置此列内容!");
		                	}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(singleWellRangeReportTemplateContentHandsontableHelper!=null&&singleWellRangeReportTemplateContentHandsontableHelper.hot!=''&&singleWellRangeReportTemplateContentHandsontableHelper.hot!=undefined && singleWellRangeReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		if(coords.col==2){
	                			var remark=singleWellRangeReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,13);
	                			if(isNotVal(remark)){
	                				var showValue=remark;
	            					var rowChar=90;
	            					var maxWidth=rowChar*10;
	            					if(remark.length>rowChar){
	            						showValue='';
	            						let arr = [];
	            						let index = 0;
	            						while(index<remark.length){
	            							arr.push(remark.slice(index,index +=rowChar));
	            						}
	            						for(var i=0;i<arr.length;i++){
	            							showValue+=arr[i];
	            							if(i<arr.length-1){
	            								showValue+='<br>';
	            							}
	            						}
	            					}
	                				if(!isNotVal(TD.tip)){
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        singleWellRangeReportTemplateContentHandsontableHelper.saveData = function () {}
	        singleWellRangeReportTemplateContentHandsontableHelper.clearContainer = function () {
	        	singleWellRangeReportTemplateContentHandsontableHelper.AllData = [];
	        }
	        return singleWellRangeReportTemplateContentHandsontableHelper;
	    }
};

function CreateSingleWellDailyReportTotalItemsInfoTable(calculateType,unitId,unitName,classes){
	Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	var templateSelection= Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
	var templateCode="";
	if(templateSelection.length>0){
		templateCode=templateSelection[0].data.templateCode;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitTimingTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==0){
				Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle('单井班报表内容配置');
			}else{
				Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(unitName+'/单井班报表内容配置');
			}
			if(singleWellDailyReportTemplateContentHandsontableHelper==null || singleWellDailyReportTemplateContentHandsontableHelper.hot==undefined){
				singleWellDailyReportTemplateContentHandsontableHelper = SingleWellDailyReportTemplateContentHandsontableHelper.createNew("ReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','报表曲线','','','','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,singleWellDailyReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,singleWellDailyReportTemplateContentHandsontableHelper);}}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,singleWellDailyReportTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'},"
						+"{data:'remark'}"
						+"]";
				singleWellDailyReportTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				singleWellDailyReportTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				singleWellDailyReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				singleWellDailyReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			calculateType: calculateType,
			reportType: 2,
			unitId: unitId,
			templateCode: templateCode,
			classes: classes
        }
	});
};

var SingleWellDailyReportTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var singleWellDailyReportTemplateContentHandsontableHelper = {};
	        singleWellDailyReportTemplateContentHandsontableHelper.hot1 = '';
	        singleWellDailyReportTemplateContentHandsontableHelper.divid = divid;
	        singleWellDailyReportTemplateContentHandsontableHelper.validresult=true;//数据校验
	        singleWellDailyReportTemplateContentHandsontableHelper.colHeaders=[];
	        singleWellDailyReportTemplateContentHandsontableHelper.columns=[];
	        singleWellDailyReportTemplateContentHandsontableHelper.AllData=[];
	        
	        singleWellDailyReportTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        singleWellDailyReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        singleWellDailyReportTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+singleWellDailyReportTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+singleWellDailyReportTemplateContentHandsontableHelper.divid);
	        	singleWellDailyReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [10,11,12,13],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,30,150,80,60,60,60,60,60,85,85],
	                columns:singleWellDailyReportTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:singleWellDailyReportTemplateContentHandsontableHelper.colHeaders,//显示列头
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
	                    	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                	if(reportUnitTreeSelectedRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                		if(selectedItem.data.classes==0){
		                			cellProperties.readOnly = true;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex<=4) {
		    							cellProperties.readOnly = true;
		    		                }else if(visualColIndex==9){
		    		                	cellProperties.renderer = singleWellDailyReportTemplateContentHandsontableHelper.addCurveBg;
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(singleWellDailyReportTemplateContentHandsontableHelper!=null && singleWellDailyReportTemplateContentHandsontableHelper.hot!=undefined){
	                		var row1=singleWellDailyReportTemplateContentHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==9)){
		                		var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                		if(reportUnitTreeSelectedRow!=''){
		                			var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                			if(selectedItem.data.classes==1){
		                				var CurveConfigWindow=Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");
		                				
		                				Ext.getCmp("curveConfigSelectedTableType_Id").setValue(23);//单井班报表内容表
		                				Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
		                				Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);
		                				
		                				CurveConfigWindow.show();
		                				
		                				var curveConfig=null;
		                				if(column==9 && isNotVal(row1[10])){
		                					curveConfig=row1[10];
		                				}
		                				var value='ff0000';
		                				
		                				if(isNotVal(curveConfig)){
		                					Ext.getCmp("curveConfigSort_Id").setValue(curveConfig.sort);
		                					Ext.getCmp("curveConfigLineWidth_Id").setValue(curveConfig.lineWidth);
		                					Ext.getCmp("curveConfigDashStyleComb_Id").setValue(curveConfig.dashStyle);
		                					Ext.getCmp("curveConfigYAxisOppositeComb_Id").setValue(curveConfig.yAxisOpposite);
		                		        	
		                		        	Ext.getCmp('curveConfigColor_id').setValue(curveConfig.color);
		                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
		                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
		                		            	background: '#'+curveConfig.color,
		                		            });
		                				}else{
		                					Ext.getCmp('curveConfigColor_id').setValue(value);
		                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
		                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
		                		            	background: '#'+value
		                		            });
		                				}
		                			}
		                		}
		                	}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(singleWellDailyReportTemplateContentHandsontableHelper!=null&&singleWellDailyReportTemplateContentHandsontableHelper.hot!=''&&singleWellDailyReportTemplateContentHandsontableHelper.hot!=undefined && singleWellDailyReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		if(coords.col==2){
	                			var remark=singleWellDailyReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,13);
	                			if(isNotVal(remark)){
	                				var showValue=remark;
	            					var rowChar=90;
	            					var maxWidth=rowChar*10;
	            					if(remark.length>rowChar){
	            						showValue='';
	            						let arr = [];
	            						let index = 0;
	            						while(index<remark.length){
	            							arr.push(remark.slice(index,index +=rowChar));
	            						}
	            						for(var i=0;i<arr.length;i++){
	            							showValue+=arr[i];
	            							if(i<arr.length-1){
	            								showValue+='<br>';
	            							}
	            						}
	            					}
	                				if(!isNotVal(TD.tip)){
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        singleWellDailyReportTemplateContentHandsontableHelper.saveData = function () {}
	        singleWellDailyReportTemplateContentHandsontableHelper.clearContainer = function () {
	        	singleWellDailyReportTemplateContentHandsontableHelper.AllData = [];
	        }
	        return singleWellDailyReportTemplateContentHandsontableHelper;
	    }
};

function CreateProtocolReportUnitPropertiesInfoTable(data){
	var root=[];
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title='根节点';
		item1.value='单元列表';
		root.push(item1);
	}else if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title='单元名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='计算类型';
		item2.value=data.calculateTypeName;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='排序序号';
		item3.value=data.sort;
		root.push(item3);
	}
	
	if(reportUnitPropertiesHandsontableHelper==null || reportUnitPropertiesHandsontableHelper.hot==undefined){
		reportUnitPropertiesHandsontableHelper = ReportUnitPropertiesHandsontableHelper.createNew("ModbusProtocolReportUnitPropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		reportUnitPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		reportUnitPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		reportUnitPropertiesHandsontableHelper.classes=data.classes;
		reportUnitPropertiesHandsontableHelper.createTable(root);
	}else{
		reportUnitPropertiesHandsontableHelper.classes=data.classes;
		reportUnitPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ReportUnitPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var reportUnitPropertiesHandsontableHelper = {};
	        reportUnitPropertiesHandsontableHelper.hot = '';
	        reportUnitPropertiesHandsontableHelper.classes =null;
	        reportUnitPropertiesHandsontableHelper.divid = divid;
	        reportUnitPropertiesHandsontableHelper.validresult=true;//数据校验
	        reportUnitPropertiesHandsontableHelper.colHeaders=[];
	        reportUnitPropertiesHandsontableHelper.columns=[];
	        reportUnitPropertiesHandsontableHelper.AllData=[];
	        
	        reportUnitPropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        reportUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        reportUnitPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+reportUnitPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportUnitPropertiesHandsontableHelper.divid);
	        	reportUnitPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,4,6],
	                columns:reportUnitPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportUnitPropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    	if (visualColIndex ==0 || visualColIndex ==1) {
								cellProperties.readOnly = true;
								cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
			                }
		                    if(reportUnitPropertiesHandsontableHelper.classes===0){
								cellProperties.readOnly = true;
								cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
			                }else if(reportUnitPropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, reportUnitPropertiesHandsontableHelper);
			                    	}
			                    }else if (visualColIndex === 2 && visualRowIndex===2) {
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, reportUnitPropertiesHandsontableHelper);
			                    	}
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
			                    	this.type = 'dropdown';
			                    	this.source = ['无','功图计算','转速计产'];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }
		                    }
	                    }else{
	                    	cellProperties.readOnly = true;
							cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        reportUnitPropertiesHandsontableHelper.saveData = function () {}
	        reportUnitPropertiesHandsontableHelper.clearContainer = function () {
	        	reportUnitPropertiesHandsontableHelper.AllData = [];
	        }
	        return reportUnitPropertiesHandsontableHelper;
	    }
};

function CreateProductionReportTemplateInfoTable(name,calculateType,code){
	Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").setTitle('区域日报模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(productionReportTemplateHandsontableHelper!=null){
				if(productionReportTemplateHandsontableHelper.hot!=undefined){
					productionReportTemplateHandsontableHelper.hot.destroy();
				}
				productionReportTemplateHandsontableHelper=null;
			}
			
			if(productionReportTemplateHandsontableHelper==null || productionReportTemplateHandsontableHelper.hot==undefined){
				productionReportTemplateHandsontableHelper = ProductionReportTemplateHandsontableHelper.createNew("ModbusProtocolReportUnitProductionTemplateTableInfoDiv_id","ModbusProtocolReportUnitProductionTemplateTableInfoContainer",result);
				productionReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reportType: 1,
			name:name,
			calculateType:calculateType,
			code:code
        }
	});
}

var ProductionReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var productionReportTemplateHandsontableHelper = {};
	        productionReportTemplateHandsontableHelper.templateData=templateData;
	        productionReportTemplateHandsontableHelper.get_data = {};
	        productionReportTemplateHandsontableHelper.data=[];
	        productionReportTemplateHandsontableHelper.hot = '';
	        productionReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        productionReportTemplateHandsontableHelper.initData=function(){
	        	productionReportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<productionReportTemplateHandsontableHelper.templateData.header.length;i++){
		        	productionReportTemplateHandsontableHelper.data.push(productionReportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        productionReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(productionReportTemplateHandsontableHelper!=null && productionReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<productionReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = productionReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        productionReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			productionReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			productionReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        productionReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        productionReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        productionReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        productionReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        productionReportTemplateHandsontableHelper.createTable = function () {
	            productionReportTemplateHandsontableHelper.container.innerHTML = "";
	            productionReportTemplateHandsontableHelper.hot = new Handsontable(productionReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: productionReportTemplateHandsontableHelper.data,
	                fixedRowsTop:productionReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: productionReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: productionReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: productionReportTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: productionReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = productionReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        productionReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	productionReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return productionReportTemplateHandsontableHelper;
	    }
	};

function CreateproductionReportTotalItemsInfoTable(calculateType,unitId,unitName,classes){
	Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	var templateSelection= Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
	var templateCode="";
	if(templateSelection.length>0){
		templateCode=templateSelection[0].data.templateCode;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==0){
				Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").setTitle('区域日报内容配置');
			}else{
				Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").setTitle(unitName+'/区域日报内容配置');
			}
			if(productionReportTemplateContentHandsontableHelper==null || productionReportTemplateContentHandsontableHelper.hot==undefined){
				productionReportTemplateContentHandsontableHelper = ProductionReportTemplateContentHandsontableHelper.createNew("ModbusProtocolProductionReportUnitContentConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','求和','求平均','报表曲线','曲线统计类型','','','','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'sumSign',type:'checkbox'}," 
						+"{data:'averageSign',type:'checkbox'}," 
						
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'curveStatType',type:'dropdown',strict:true,allowInvalid:false,source:['合计', '平均']},"
						
						+"{data:'reportCurveConf'},"

						+"{data:'code'},"
						+"{data:'dataType'},"
						+"{data:'remark'}"
						+"]";
				productionReportTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				productionReportTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				productionReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				productionReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			calculateType: calculateType,
			reportType: 1,
			unitId: unitId,
			templateSelection: templateSelection,
			classes: classes
        }
	});
};

var ProductionReportTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var productionReportTemplateContentHandsontableHelper = {};
	        productionReportTemplateContentHandsontableHelper.hot1 = '';
	        productionReportTemplateContentHandsontableHelper.divid = divid;
	        productionReportTemplateContentHandsontableHelper.validresult=true;//数据校验
	        productionReportTemplateContentHandsontableHelper.colHeaders=[];
	        productionReportTemplateContentHandsontableHelper.columns=[];
	        productionReportTemplateContentHandsontableHelper.AllData=[];
	        
	        productionReportTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        productionReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        productionReportTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+productionReportTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+productionReportTemplateContentHandsontableHelper.divid);
	        	productionReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [13,14,15,16],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,30,150,60,60,70,60,60,60,30,45,85,70],
	                columns:productionReportTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:productionReportTemplateContentHandsontableHelper.colHeaders,//显示列头
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
	                    	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                	if(reportUnitTreeSelectedRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                		if(selectedItem.data.classes==0){
		                			cellProperties.readOnly = true;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex<=4) {
		    							cellProperties.readOnly = true;
		    		                }else if(visualColIndex==11){
		    		                	cellProperties.renderer = productionReportTemplateContentHandsontableHelper.addCurveBg;
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(productionReportTemplateContentHandsontableHelper!=null && productionReportTemplateContentHandsontableHelper.hot!=undefined){
	                		var row1=productionReportTemplateContentHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==11)){
		                		var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                		if(reportUnitTreeSelectedRow!=''){
		                			var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                			if(selectedItem.data.classes==1){
		                				var CurveConfigWindow=Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");
		                				
		                				Ext.getCmp("curveConfigSelectedTableType_Id").setValue(22);//区域报表内容
		                				Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
		                				Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);
		                				
		                				CurveConfigWindow.show();
		                				
		                				var curveConfig=null;
		                				if(column==11 && isNotVal(row1[13])){
		                					curveConfig=row1[13];
		                				}
		                				var value='ff0000';
		                				
		                				if(isNotVal(curveConfig)){
		                					Ext.getCmp("curveConfigSort_Id").setValue(curveConfig.sort);
		                					Ext.getCmp("curveConfigLineWidth_Id").setValue(curveConfig.lineWidth);
		                					Ext.getCmp("curveConfigDashStyleComb_Id").setValue(curveConfig.dashStyle);
		                					Ext.getCmp("curveConfigYAxisOppositeComb_Id").setValue(curveConfig.yAxisOpposite);
		                		        	
		                		        	Ext.getCmp('curveConfigColor_id').setValue(curveConfig.color);
		                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
		                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
		                		            	background: '#'+curveConfig.color,
		                		            });
		                				}else{
		                					Ext.getCmp('curveConfigColor_id').setValue(value);
		                		            var Color0=Ext.getCmp('curveConfigColor_id').color;
		                		            Ext.getCmp('curveConfigColor_id').inputEl.applyStyles({
		                		            	background: '#'+value
		                		            });
		                				}
		                			}
		                		}
		                	}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(productionReportTemplateContentHandsontableHelper!=null&&productionReportTemplateContentHandsontableHelper.hot!=''&&productionReportTemplateContentHandsontableHelper.hot!=undefined && productionReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		if(coords.col==2){
	                			var remark=productionReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,16);
	                			if(isNotVal(remark)){
	                				var showValue=remark;
	            					var rowChar=90;
	            					var maxWidth=rowChar*10;
	            					if(remark.length>rowChar){
	            						showValue='';
	            						let arr = [];
	            						let index = 0;
	            						while(index<remark.length){
	            							arr.push(remark.slice(index,index +=rowChar));
	            						}
	            						for(var i=0;i<arr.length;i++){
	            							showValue+=arr[i];
	            							if(i<arr.length-1){
	            								showValue+='<br>';
	            							}
	            						}
	            					}
	                				if(!isNotVal(TD.tip)){
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        productionReportTemplateContentHandsontableHelper.saveData = function () {}
	        productionReportTemplateContentHandsontableHelper.clearContainer = function () {
	        	productionReportTemplateContentHandsontableHelper.AllData = [];
	        }
	        return productionReportTemplateContentHandsontableHelper;
	    }
};

function SaveReportUnitData(){
	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	if(reportUnitTreeSelectedRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		var propertiesData=reportUnitPropertiesHandsontableHelper.hot.getData();
		var reportUnitProperties={};
		if(selectedItem.data.classes==1){//选中的是单元
			reportUnitProperties.classes=selectedItem.data.classes;
			reportUnitProperties.id=selectedItem.data.id;
			reportUnitProperties.unitCode=selectedItem.data.code;
			reportUnitProperties.unitName=propertiesData[0][2];
			
			reportUnitProperties.singleWellRangeReportTemplate=selectedItem.data.singleWellRangeReportTemplate;
			reportUnitProperties.singleWellDailyReportTemplate=selectedItem.data.singleWellDailyReportTemplate;
			reportUnitProperties.productionReportTemplate=selectedItem.data.productionReportTemplate;
			
			var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportTemplateTabPanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
				var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
				if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id'){
					var templateSelection= Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
					if(templateSelection.length>0){
						reportUnitProperties.singleWellDailyReportTemplate=templateSelection[0].data.templateCode;
					}
				}else if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id'){
					var templateSelection= Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
					if(templateSelection.length>0){
						reportUnitProperties.singleWellRangeReportTemplate=templateSelection[0].data.templateCode;
					}
				}
			}else if(activeId=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
				var templateSelection= Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
				if(templateSelection.length>0){
					reportUnitProperties.productionReportTemplate=templateSelection[0].data.templateCode;
				}
			}
			
//			reportUnitProperties.calculateType=(propertiesData[1][2]=="抽油机井"?0:1);
			reportUnitProperties.calculateType=0;
			if(propertiesData[1][2]=="功图计算"){
				reportUnitProperties.calculateType=1;
			}else if(propertiesData[1][2]=="转速计产"){
				reportUnitProperties.calculateType=2;
			}
			reportUnitProperties.sort=propertiesData[2][2];
		}
		if(selectedItem.data.classes==1){//保存单元
			SaveModbusProtocolReportUnitData(reportUnitProperties);
//			grantReportTotalCalItemsPermission(reportUnitProperties.calculateType);
		}
	}
};

function SaveModbusProtocolReportUnitData(saveData){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolReportUnitData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.MessageBox.alert("信息","保存成功");
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert("信息","显示单元数据保存失败");
            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			data: JSON.stringify(saveData),
        }
	});
}


var grantReportUnitContentItemsPermission = function (unitId,reportType,calculateType,sort,itemsConfigData) {
    var addUrl = context + '/acquisitionUnitManagerController/grantReportUnitContentItemsPermission';
    // 添加条件
    var saveData={};
    saveData.itemList=[];
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (!isNotVal(unitId)) {
        return false
    }
    Ext.Array.each(itemsConfigData, function (name, index, countriesItSelf) {
        if ((itemsConfigData[index][0]+'')==='true') {
        	var item={};
        	item.matrix='0,0,0';
        	if(reportType==0){
        		item.itemName = itemsConfigData[index][2];
        		item.totalType=0;
        		item.dataSource=itemsConfigData[index][4];
        		item.itemShowLevel = itemsConfigData[index][6];
        		item.itemSort = sort;
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(itemsConfigData[index][8]) && isNotVal(itemsConfigData[index][9])){
    				reportCurveConfig=itemsConfigData[index][9];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = itemsConfigData[index][10];
        		item.dataType = itemsConfigData[index][11];
        		
        		if(item.dataType==2){
        			item.itemPrec=itemsConfigData[index][8];
        			
        			if(item.dataSource=='采集'){
        				if(itemsConfigData[index][5]=='最大值'){
                			item.totalType=1;
                		}else if(itemsConfigData[index][5]=='最小值'){
                			item.totalType=2;
                		}else if(itemsConfigData[index][5]=='平均值'){
                			item.totalType=3;
                		}else if(itemsConfigData[index][5]=='最新值'){
                			item.totalType=4;
                		}else if(itemsConfigData[index][5]=='最旧值'){
                			item.totalType=5;
                		}else if(itemsConfigData[index][5]=='日累计'){
                			item.totalType=6;
                		}
        			}
        		}
        	}else if(reportType==2){
        		item.itemName = itemsConfigData[index][2];
        		item.totalType=0;
        		item.dataSource=itemsConfigData[index][4];
        		item.itemShowLevel = itemsConfigData[index][6];
        		item.itemSort = itemsConfigData[index][7];
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(itemsConfigData[index][9]) && isNotVal(itemsConfigData[index][10])){
    				reportCurveConfig=itemsConfigData[index][10];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = itemsConfigData[index][11];
        		item.dataType = itemsConfigData[index][12];
        		
        		if(item.dataType==2){
        			item.itemPrec=itemsConfigData[index][8];
        			
        			if(item.dataSource=='采集'){
        				if(itemsConfigData[index][5]=='最大值'){
                			item.totalType=1;
                		}else if(itemsConfigData[index][5]=='最小值'){
                			item.totalType=2;
                		}else if(itemsConfigData[index][5]=='平均值'){
                			item.totalType=3;
                		}else if(itemsConfigData[index][5]=='最新值'){
                			item.totalType=4;
                		}else if(itemsConfigData[index][5]=='最旧值'){
                			item.totalType=5;
                		}else if(itemsConfigData[index][5]=='日累计'){
                			item.totalType=6;
                		}
        			}
        		}
        	}else if(reportType==1){
        		item.itemName = itemsConfigData[index][2];
        		
        		item.totalType=0;
        		item.dataSource=itemsConfigData[index][4];
        		item.itemShowLevel = itemsConfigData[index][6];
        		item.itemSort = itemsConfigData[index][7];
        		
        		item.sumSign='0';
        		if((itemsConfigData[index][9]+'')==='true'){
        			item.sumSign='1';
        		}
        		
        		item.averageSign='0';
        		if((itemsConfigData[index][10]+'')==='true'){
        			item.averageSign='1';
        		}
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(itemsConfigData[index][11]) && isNotVal(itemsConfigData[index][13])){
    				reportCurveConfig=itemsConfigData[index][13];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
        		
        		item.curveStatType = itemsConfigData[index][12];
        		if(itemsConfigData[index][12]=='合计'){
        			item.curveStatType='1';
        		}else if(itemsConfigData[index][12]=='平均'){
        			item.curveStatType='2';
        		}else if(itemsConfigData[index][12]=='最大值'){
        			item.curveStatType='3';
        		}else if(itemsConfigData[index][12]=='最小值'){
        			item.curveStatType='4';
        		}
            	
        		item.itemCode = itemsConfigData[index][14];
        		item.dataType = itemsConfigData[index][15]+"";
        		
        		if(item.dataType==2){
        			item.itemPrec=itemsConfigData[index][8];
        			
        			if(item.dataSource=='采集'){
        				if(itemsConfigData[index][5]=='最大值'){
                			item.totalType=1;
                		}else if(itemsConfigData[index][5]=='最小值'){
                			item.totalType=2;
                		}else if(itemsConfigData[index][5]=='平均值'){
                			item.totalType=3;
                		}else if(itemsConfigData[index][5]=='最新值'){
                			item.totalType=4;
                		}else if(itemsConfigData[index][5]=='最旧值'){
                			item.totalType=5;
                		}else if(itemsConfigData[index][5]=='日累计'){
                			item.totalType=6;
                		}
        			}
        		}
        	}
        	saveData.itemList.push(item);
        }
    });
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        params: {
            unitId: unitId,
            reportType: reportType,
            calculateType: calculateType,
            sort:sort,
            saveData: JSON.stringify(saveData)
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(cosog.string.ts, "<font color=blue>保存成功</font>");
                if(reportType==0){
                	CreateSingleWellRangeReportTotalItemsInfoTable();
                }else if(reportType==1){
                	
                }else if(reportType==2){
                	
                }
                
                
            }
            if (result.msg == false) {
                Ext.Msg.alert('info', "<font color=red>SORRY！" + '计算项安排失败' + "。</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
    return false;
}


var grantReportTotalCalItemsPermission = function (calculateType) {
	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	var reportType=0;
	var calItemsData = null;
	var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportTemplateTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
		var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
		if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id'){
			if (singleWellDailyReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow=='') {
		        return false;
		    }
			reportType=2;
			calItemsData = singleWellDailyReportTemplateContentHandsontableHelper.hot.getData();
		}else if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id'){
			if (singleWellRangeReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow=='') {
		        return false;
		    }
			reportType=0;
			calItemsData = singleWellRangeReportTemplateContentHandsontableHelper.hot.getData();
		}
	}else if(activeId=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
		if (productionReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow=='') {
	        return false;
	    }
		reportType=1;
		calItemsData = productionReportTemplateContentHandsontableHelper.hot.getData();
	}
	
	var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
    var addUrl = context + '/acquisitionUnitManagerController/grantTotalCalItemsToReportUnitPermission';
	
    // 添加条件
    var saveData={};
    saveData.itemList=[];
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    
    var unitId = selectedItem.data.id;
    if (!isNotVal(unitId)) {
        return false
    }

    Ext.Array.each(calItemsData, function (name, index, countriesItSelf) {
        if ((calItemsData[index][0]+'')==='true') {
        	var item={};
        	item.matrix='0,0,0';
        	if(reportType==0){
        		item.itemName = calItemsData[index][2];
        		item.totalType=0;
        		item.dataSource=calItemsData[index][4];
        		item.itemShowLevel = calItemsData[index][6];
        		item.itemSort = calItemsData[index][7];
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(calItemsData[index][9]) && isNotVal(calItemsData[index][10])){
    				reportCurveConfig=calItemsData[index][10];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = calItemsData[index][11];
        		item.dataType = calItemsData[index][12];
        		
        		if(item.dataType==2){
        			item.itemPrec=calItemsData[index][8];
        			
        			if(item.dataSource=='采集'){
        				if(calItemsData[index][5]=='最大值'){
                			item.totalType=1;
                		}else if(calItemsData[index][5]=='最小值'){
                			item.totalType=2;
                		}else if(calItemsData[index][5]=='平均值'){
                			item.totalType=3;
                		}else if(calItemsData[index][5]=='最新值'){
                			item.totalType=4;
                		}else if(calItemsData[index][5]=='最旧值'){
                			item.totalType=5;
                		}else if(calItemsData[index][5]=='日累计'){
                			item.totalType=6;
                		}
        			}
        		}
        	}else if(reportType==2){
        		item.itemName = calItemsData[index][2];
        		item.totalType=0;
        		item.dataSource=calItemsData[index][4];
        		item.itemShowLevel = calItemsData[index][6];
        		item.itemSort = calItemsData[index][7];
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(calItemsData[index][9]) && isNotVal(calItemsData[index][10])){
    				reportCurveConfig=calItemsData[index][10];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = calItemsData[index][11];
        		item.dataType = calItemsData[index][12];
        		
        		if(item.dataType==2){
        			item.itemPrec=calItemsData[index][8];
        			
        			if(item.dataSource=='采集'){
        				if(calItemsData[index][5]=='最大值'){
                			item.totalType=1;
                		}else if(calItemsData[index][5]=='最小值'){
                			item.totalType=2;
                		}else if(calItemsData[index][5]=='平均值'){
                			item.totalType=3;
                		}else if(calItemsData[index][5]=='最新值'){
                			item.totalType=4;
                		}else if(calItemsData[index][5]=='最旧值'){
                			item.totalType=5;
                		}else if(calItemsData[index][5]=='日累计'){
                			item.totalType=6;
                		}
        			}
        		}
        	}else if(reportType==1){
        		item.itemName = calItemsData[index][2];
        		
        		item.totalType=0;
        		item.dataSource=calItemsData[index][4];
        		item.itemShowLevel = calItemsData[index][6];
        		item.itemSort = calItemsData[index][7];
        		
        		item.sumSign='0';
        		if((calItemsData[index][9]+'')==='true'){
        			item.sumSign='1';
        		}
        		
        		item.averageSign='0';
        		if((calItemsData[index][10]+'')==='true'){
        			item.averageSign='1';
        		}
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(calItemsData[index][11]) && isNotVal(calItemsData[index][13])){
    				reportCurveConfig=calItemsData[index][13];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
        		
        		item.curveStatType = calItemsData[index][12];
        		if(calItemsData[index][12]=='合计'){
        			item.curveStatType='1';
        		}else if(calItemsData[index][12]=='平均'){
        			item.curveStatType='2';
        		}else if(calItemsData[index][12]=='最大值'){
        			item.curveStatType='3';
        		}else if(calItemsData[index][12]=='最小值'){
        			item.curveStatType='4';
        		}
            	
        		item.itemCode = calItemsData[index][14];
        		item.dataType = calItemsData[index][15]+"";
        		
        		if(item.dataType==2){
        			item.itemPrec=calItemsData[index][8];
        			
        			if(item.dataSource=='采集'){
        				if(calItemsData[index][5]=='最大值'){
                			item.totalType=1;
                		}else if(calItemsData[index][5]=='最小值'){
                			item.totalType=2;
                		}else if(calItemsData[index][5]=='平均值'){
                			item.totalType=3;
                		}else if(calItemsData[index][5]=='最新值'){
                			item.totalType=4;
                		}else if(calItemsData[index][5]=='最旧值'){
                			item.totalType=5;
                		}else if(calItemsData[index][5]=='日累计'){
                			item.totalType=6;
                		}
        			}
        		}
        	}
        	saveData.itemList.push(item);
        }
    });
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        params: {
            unitId: unitId,
            reportType: reportType,
            calculateType: calculateType,
            saveData: JSON.stringify(saveData)
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(cosog.string.ts, "<font color=blue>保存成功</font>");
            }
            if (result.msg == false) {
                Ext.Msg.alert('info', "<font color=red>SORRY！" + '计算项安排失败' + "。</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
    return false;
}

function getReportType(){
	var reportType=0;
	var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportTemplateTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
		var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
		if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id'){
			reportType=2;
		}else if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id'){
			reportType=0;
		}
	}else if(activeId=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
		reportType=1;
	}
	return reportType;
}