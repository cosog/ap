var reportInstanceSingleWellTemplateHandsontableHelper=null;
var reportInstanceSingleWellTemplateContentHandsontableHelper=null;

var reportInstanceProductionTemplateHandsontableHelper=null;
var reportInstanceProductionTemplateContentHandsontableHelper=null;

var protocolReportInstancePropertiesHandsontableHelper=null;
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
                    text: cosog.string.refresh,
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
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolReportInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveReportInstanceData();
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
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'报表实例列表',
                    	layout: 'fit',
                    	border: false,
                    	id:"ModbusProtocolReportInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
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
                    	title:'单井日报',
                    	id:'ModbusProtocolReportInstanceSingleWellReportTemplatePanel_Id',
                    	border: false,
                    	region: 'center',
                    	layout: "border",
                    	items: [{
                    		region: 'center',
                    		layout: "border",
                    		items: [{
                        		region: 'center',
                        		title:'单井日报模板',
                        		id:"ModbusProtocolReportInstanceTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ModbusProtocolReportInstanceTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstanceTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (reportInstanceSingleWellTemplateHandsontableHelper != null && reportInstanceSingleWellTemplateHandsontableHelper.hot != null && reportInstanceSingleWellTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                        	reportInstanceSingleWellTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'单井日报内容',
                            	border: false,
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	id:"ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ModbusProtocolReportInstanceContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstanceContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (reportInstanceSingleWellTemplateContentHandsontableHelper != null && reportInstanceSingleWellTemplateContentHandsontableHelper.hot != null && reportInstanceSingleWellTemplateContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		reportInstanceSingleWellTemplateContentHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}]
                    	}]
                    },{
                    	title:'区域日报',
                    	id:'ModbusProtocolReportInstanceProductionReportTemplatePanel_Id',
                    	border: false,
                    	region: 'center',
                    	layout: "border",
                    	items: [{
                    		region: 'center',
                    		layout: "border",
                    		items: [{
                        		region: 'center',
                        		title:'区域日报模板',
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
                    	}]
                    }],
                    listeners: {
                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                        	var reportType=0;
                        	var selectedUnitId='';
                        	var selectedTemplateCode='';
                        	var selectedInstanceName='';
                        	if(newCard.id=="ModbusProtocolReportInstanceSingleWellReportTemplatePanel_Id"){
                        		reportType=0;
                        	}else if(newCard.id=="ModbusProtocolReportInstanceProductionReportTemplatePanel_Id"){
                        		reportType=1;
                        	}
                        	var instanceTreeSelection= Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getSelectionModel().getSelection();
            				var selectedUnitId=0;
                        	if(instanceTreeSelection.length>0){
            					var record=instanceTreeSelection[0];
            					if(record.data.classes==0){//选中设备类型deviceType
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			selectedUnitId=record.data.children[0].unitId;
                            			selectedInstanceName=record.data.children[0].text;
                            			if(reportType==0){
                            				selectedTemplateCode=record.data.children[0].singleWellReportTemplate;
                            			}else{
                            				selectedTemplateCode=record.data.children[0].productionReportTemplate;
                            			}
                            		}
                            	}else if(record.data.classes==2){//选中报表单元
                            		selectedUnitId=record.data.unitId;
                            		selectedInstanceName=record.parentNode.data.text;
                            		if(reportType==0){
                        				selectedTemplateCode=record.data.singleWellReportTemplate;
                        			}else{
                        				selectedTemplateCode=record.data.productionReportTemplate;
                        			}
                            	}else{
                            		selectedUnitId=record.data.unitId;
                            		selectedInstanceName=record.data.text;
                            		if(reportType==0){
                        				selectedTemplateCode=record.data.singleWellReportTemplate;
                        			}else{
                        				selectedTemplateCode=record.data.productionReportTemplate;
                        			}
                            	}
                            	
                            	
                            	if(reportType==0){
                        			CreateReportInstanceSingleWellTemplateInfoTable(record.data.deviceType,selectedTemplateCode,selectedInstanceName);
                        			CreateSingleWellReportInstanceTotalItemsInfoTable(record.data.deviceType,selectedUnitId,selectedInstanceName);
                            	}else{
                            		CreateReportInstanceProductionTemplateInfoTable(record.data.deviceType,selectedTemplateCode,selectedInstanceName);
                            		CreateProductionReportInstanceTotalItemsInfoTable(record.data.deviceType,selectedUnitId,selectedInstanceName);
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

function CreateReportInstanceSingleWellTemplateInfoTable(deviceType,code,selectedInstanceName){
	Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").setTitle(selectedInstanceName+'/单井日报模板');
			var result =  Ext.JSON.decode(response.responseText);
			
			if(reportInstanceSingleWellTemplateHandsontableHelper!=null){
				if(reportInstanceSingleWellTemplateHandsontableHelper.hot!=undefined){
					reportInstanceSingleWellTemplateHandsontableHelper.hot.destroy();
				}
				reportInstanceSingleWellTemplateHandsontableHelper=null;
			}
			
			if(reportInstanceSingleWellTemplateHandsontableHelper==null || reportInstanceSingleWellTemplateHandsontableHelper.hot==undefined){
				reportInstanceSingleWellTemplateHandsontableHelper = ReportInstanceSingleWellTemplateHandsontableHelper.createNew("ModbusProtocolReportInstanceTemplateTableInfoDiv_id","ModbusProtocolReportInstanceTemplateTableInfoContainer",result);
				reportInstanceSingleWellTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reportType:0,
			deviceType:deviceType,
			code:code
        }
	});
}

var ReportInstanceSingleWellTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var reportInstanceSingleWellTemplateHandsontableHelper = {};
	        reportInstanceSingleWellTemplateHandsontableHelper.templateData=templateData;
	        reportInstanceSingleWellTemplateHandsontableHelper.get_data = {};
	        reportInstanceSingleWellTemplateHandsontableHelper.data=[];
	        reportInstanceSingleWellTemplateHandsontableHelper.hot = '';
	        reportInstanceSingleWellTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        reportInstanceSingleWellTemplateHandsontableHelper.initData=function(){
	        	reportInstanceSingleWellTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<reportInstanceSingleWellTemplateHandsontableHelper.templateData.header.length;i++){
		        	reportInstanceSingleWellTemplateHandsontableHelper.data.push(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        reportInstanceSingleWellTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(reportInstanceSingleWellTemplateHandsontableHelper!=null && reportInstanceSingleWellTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<reportInstanceSingleWellTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = reportInstanceSingleWellTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        reportInstanceSingleWellTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			reportInstanceSingleWellTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			reportInstanceSingleWellTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        reportInstanceSingleWellTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        reportInstanceSingleWellTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        reportInstanceSingleWellTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        reportInstanceSingleWellTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        reportInstanceSingleWellTemplateHandsontableHelper.createTable = function () {
	            reportInstanceSingleWellTemplateHandsontableHelper.container.innerHTML = "";
	            reportInstanceSingleWellTemplateHandsontableHelper.hot = new Handsontable(reportInstanceSingleWellTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: reportInstanceSingleWellTemplateHandsontableHelper.data,
	                fixedRowsTop:reportInstanceSingleWellTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: reportInstanceSingleWellTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: reportInstanceSingleWellTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: reportInstanceSingleWellTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: reportInstanceSingleWellTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = reportInstanceSingleWellTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        reportInstanceSingleWellTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	reportInstanceSingleWellTemplateHandsontableHelper.initData();
	        }

	        init();
	        return reportInstanceSingleWellTemplateHandsontableHelper;
	    }
	};

function CreateSingleWellReportInstanceTotalItemsInfoTable(deviceType,selectedUnitId,selectedInstanceName){
	Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportInstanceTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(selectedInstanceName)){
				Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").setTitle(selectedInstanceName+'/单井日报内容');
			}else{
				Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").setTitle('单井日报内容');
			}
			if(reportInstanceSingleWellTemplateContentHandsontableHelper==null || reportInstanceSingleWellTemplateContentHandsontableHelper.hot==undefined){
				reportInstanceSingleWellTemplateContentHandsontableHelper = ReportInstanceSingleWellTemplateContentHandsontableHelper.createNew("ModbusProtocolReportInstanceContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','显示顺序','报表曲线顺序','报表曲线颜色','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceSingleWellTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurveColor'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				reportInstanceSingleWellTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportInstanceSingleWellTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportInstanceSingleWellTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportInstanceSingleWellTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType:deviceType,
			unitId:selectedUnitId,
			reportType:0
        }
	});
};

var ReportInstanceSingleWellTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var reportInstanceSingleWellTemplateContentHandsontableHelper = {};
	        reportInstanceSingleWellTemplateContentHandsontableHelper.hot1 = '';
	        reportInstanceSingleWellTemplateContentHandsontableHelper.divid = divid;
	        reportInstanceSingleWellTemplateContentHandsontableHelper.validresult=true;//数据校验
	        reportInstanceSingleWellTemplateContentHandsontableHelper.colHeaders=[];
	        reportInstanceSingleWellTemplateContentHandsontableHelper.columns=[];
	        reportInstanceSingleWellTemplateContentHandsontableHelper.AllData=[];
	        
	        reportInstanceSingleWellTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        reportInstanceSingleWellTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        reportInstanceSingleWellTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportInstanceSingleWellTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportInstanceSingleWellTemplateContentHandsontableHelper.divid);
	        	reportInstanceSingleWellTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [7,8],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,85,85],
	                columns:reportInstanceSingleWellTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportInstanceSingleWellTemplateContentHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==6){
		                	cellProperties.renderer = reportInstanceSingleWellTemplateContentHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        reportInstanceSingleWellTemplateContentHandsontableHelper.saveData = function () {}
	        reportInstanceSingleWellTemplateContentHandsontableHelper.clearContainer = function () {
	        	reportInstanceSingleWellTemplateContentHandsontableHelper.AllData = [];
	        }
	        return reportInstanceSingleWellTemplateContentHandsontableHelper;
	    }
};

function CreateProductionReportInstanceTotalItemsInfoTable(deviceType,selectedUnitId,selectedInstanceName){
	Ext.getCmp("ProductionReportInstanceContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
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
				var colHeaders="['序号','名称','单位','显示级别','显示顺序','求和','求平均','报表曲线顺序','报表曲线颜色','曲线统计类型','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'sumSign',type:'checkbox'}," 
						+"{data:'averageSign',type:'checkbox'}," 
						
						+"{data:'reportCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurveColor'},"
						
						+"{data:'curveStatType',type:'dropdown',strict:true,allowInvalid:false,source:['合计', '平均']},"
						
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
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType:deviceType,
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
	        
	        reportInstanceProductionTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        reportInstanceProductionTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        reportInstanceProductionTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportInstanceProductionTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportInstanceProductionTemplateContentHandsontableHelper.divid);
	        	reportInstanceProductionTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [10,11],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,30,45,85,85,70],
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
	                    if(visualColIndex==8){
		                	cellProperties.renderer = reportInstanceProductionTemplateContentHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
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
			saveData.deviceType=(propertiesData[1][2]=="抽油机井"?0:1);
			saveData.unitName=propertiesData[2][2];
			saveData.sort=propertiesData[3][2];
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
				Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getStore().load();
            	Ext.MessageBox.alert("信息","保存成功");
            } else {
            	Ext.MessageBox.alert("信息","显示实例数据保存失败");
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

function CreateProtocolReportInstancePropertiesInfoTable(data){
	var root=[];
	var rpcReportUnit=[];
	var pcpReportUnit=[];
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			rpcReportUnit=result.rpcUnit;
			pcpReportUnit=result.pcpUnit;
			
			if(data.classes==1){
				var item1={};
				item1.id=1;
				item1.title='实例名称';
				item1.value=data.text;
				root.push(item1);
				
				var item2={};
				item2.id=2;
				item2.title='设备类型';
				item2.value=(data.deviceType==0?"抽油机井":"螺杆泵井");
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title='报表单元';
				item3.value=data.unitName;
				root.push(item3);
				
				var item4={};
				item4.id=4;
				item4.title='排序序号';
				item4.value=data.sort;
				root.push(item4);
			}
			
			if(protocolReportInstancePropertiesHandsontableHelper==null || protocolReportInstancePropertiesHandsontableHelper.hot==undefined){
				protocolReportInstancePropertiesHandsontableHelper = ProtocolReportInstancePropertiesHandsontableHelper.createNew("ModbusProtocolReportInstancePropertiesTableInfoDiv_id");
				var colHeaders="['序号','名称','变量']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolReportInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolReportInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolReportInstancePropertiesHandsontableHelper.rpcReportUnit=rpcReportUnit;
				protocolReportInstancePropertiesHandsontableHelper.pcpReportUnit=pcpReportUnit;
				protocolReportInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolReportInstancePropertiesHandsontableHelper.rpcReportUnit=rpcReportUnit;
				protocolReportInstancePropertiesHandsontableHelper.pcpReportUnit=pcpReportUnit;
				protocolReportInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
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
	        protocolReportInstancePropertiesHandsontableHelper.rpcReportUnit=[];
			protocolReportInstancePropertiesHandsontableHelper.pcpReportUnit=[];
	        
	        protocolReportInstancePropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolReportInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
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
	                    if (visualColIndex ==0 || visualColIndex ==1) {
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    if(protocolReportInstancePropertiesHandsontableHelper.classes===1){
	                    	if(visualColIndex === 2 && visualRowIndex===0){
		                    	this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolReportInstancePropertiesHandsontableHelper);
		                    	}
		                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    	this.type = 'dropdown';
		                    	this.source = ['抽油机井','螺杆泵井'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }else if(visualColIndex === 2 && visualRowIndex===3){
		                    	this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolReportInstancePropertiesHandsontableHelper);
		                    	}
		                    }else if (visualColIndex === 2 && visualRowIndex===2) {
		                    	var deviceType='';
		                    	if(isNotVal(protocolReportInstancePropertiesHandsontableHelper.hot)){
		                    		deviceType=protocolReportInstancePropertiesHandsontableHelper.hot.getDataAtCell(1,2);
		                    	}
		                    	
	                    		this.type = 'dropdown';
	                    		if(deviceType==='抽油机井'){
	                    			this.source = protocolReportInstancePropertiesHandsontableHelper.rpcReportUnit;
	                    		}else{
	                    			this.source = protocolReportInstancePropertiesHandsontableHelper.pcpReportUnit;
	                    		}
		                    	
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
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
function CreateReportInstanceProductionTemplateInfoTable(deviceType,code,selectedInstanceName){
	Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportInstanceProductionTemplateTableInfoPanel_Id").setTitle(selectedInstanceName+'/区域日报模板');
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
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reportType:1,
			deviceType:deviceType,
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
	        	for(var i=0;i<reportInstanceProductionTemplateHandsontableHelper.templateData.header.length;i++){
		        	reportInstanceProductionTemplateHandsontableHelper.data.push(reportInstanceProductionTemplateHandsontableHelper.templateData.header[i].title);
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
	                afterChange:function(changes, source){}
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