var singleWellReportTemplateHandsontableHelper=null;
var singleWellReportTemplateContentHandsontableHelper=null;
var reportUnitPropertiesHandsontableHelper=null;

var productionReportTemplateHandsontableHelper=null;
var productionReportTemplateContentHandsontableHelper=null;


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
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addReportUnitInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
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
                    	title:'单井日报',
                    	id:'ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id',
                    	layout: "border",
                    	border: false,
                    	items: [{
                    		region: 'west',
                        	width:'16%',
                        	layout: 'fit',
                            border: false,
                            id:'ReportUnitSingleWellReportTemplateListPanel_Id',
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
                        		title:'单井日报模板',
                        		id:"ModbusProtocolReportUnitTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ModbusProtocolReportUnitTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (singleWellReportTemplateHandsontableHelper != null && singleWellReportTemplateHandsontableHelper.hot != null && singleWellReportTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                        	singleWellReportTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'单井日报内容配置',
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	border: false,
                            	id:"ModbusProtocolReportUnitContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ModbusProtocolReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (singleWellReportTemplateContentHandsontableHelper != null && singleWellReportTemplateContentHandsontableHelper.hot != null && singleWellReportTemplateContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		singleWellReportTemplateContentHandsontableHelper.hot.updateSettings({
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
                    	id:'ModbusProtocolReportUnitProductionReportTemplatePanel_Id',
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
                        		title:'区域日报模板',
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
                            	title:'区域日报内容配置',
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
                        		var ReportUnitSingleWellReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellReportTemplateListGridPanel_Id");
                            	if (isNotVal(ReportUnitSingleWellReportTemplateListGridPanel)) {
                            		ReportUnitSingleWellReportTemplateListGridPanel.getStore().load();
                            	}else{
                            		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellReportTemplateStore')
                            	}
                            	CreateSingleWellReportTotalItemsInfoTable(record.data.deviceType,selectedUnitId,record.data.text,record.data.classes);
                        	}else if(newCard.id=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
                        		var ReportUnitProductionReportTemplateListGridPanel=Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id");
                            	if (isNotVal(ReportUnitProductionReportTemplateListGridPanel)) {
                            		ReportUnitProductionReportTemplateListGridPanel.getStore().load();
                            	}else{
                            		Ext.create('AP.store.acquisitionUnit.ModbusProtocolProductionReportTemplateStore')
                            	}
                            	CreateproductionReportTotalItemsInfoTable(record.data.deviceType,selectedUnitId,record.data.text,record.data.classes);
                        	}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateSingleWellReportTemplateInfoTable(name,deviceType,code){
	Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").setTitle('单井日报模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(singleWellReportTemplateHandsontableHelper!=null){
				if(singleWellReportTemplateHandsontableHelper.hot!=undefined){
					singleWellReportTemplateHandsontableHelper.hot.destroy();
				}
				singleWellReportTemplateHandsontableHelper=null;
			}
			
			if(singleWellReportTemplateHandsontableHelper==null || singleWellReportTemplateHandsontableHelper.hot==undefined){
				singleWellReportTemplateHandsontableHelper = SingleWellReportTemplateHandsontableHelper.createNew("ModbusProtocolReportUnitTemplateTableInfoDiv_id","ModbusProtocolReportUnitTemplateTableInfoContainer",result);
				singleWellReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			reportType:0,
			deviceType:deviceType,
			code:code
        }
	});
}

var SingleWellReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var singleWellReportTemplateHandsontableHelper = {};
	        singleWellReportTemplateHandsontableHelper.templateData=templateData;
	        singleWellReportTemplateHandsontableHelper.get_data = {};
	        singleWellReportTemplateHandsontableHelper.data=[];
	        singleWellReportTemplateHandsontableHelper.hot = '';
	        singleWellReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        singleWellReportTemplateHandsontableHelper.initData=function(){
	        	singleWellReportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<singleWellReportTemplateHandsontableHelper.templateData.header.length;i++){
		        	singleWellReportTemplateHandsontableHelper.data.push(singleWellReportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        singleWellReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(singleWellReportTemplateHandsontableHelper!=null && singleWellReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<singleWellReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = singleWellReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        singleWellReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			singleWellReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			singleWellReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        singleWellReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        singleWellReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        singleWellReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        singleWellReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        singleWellReportTemplateHandsontableHelper.createTable = function () {
	            singleWellReportTemplateHandsontableHelper.container.innerHTML = "";
	            singleWellReportTemplateHandsontableHelper.hot = new Handsontable(singleWellReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: singleWellReportTemplateHandsontableHelper.data,
	                fixedRowsTop:singleWellReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: singleWellReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: singleWellReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: singleWellReportTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: singleWellReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = singleWellReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        singleWellReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	singleWellReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return singleWellReportTemplateHandsontableHelper;
	    }
	};

function CreateSingleWellReportTotalItemsInfoTable(deviceType,unitId,unitName,classes){
	Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==0){
				Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").setTitle('单井日报内容配置');
			}else{
				Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").setTitle(unitName+'/单井日报内容配置');
			}
			if(singleWellReportTemplateContentHandsontableHelper==null || singleWellReportTemplateContentHandsontableHelper.hot==undefined){
				singleWellReportTemplateContentHandsontableHelper = SingleWellReportTemplateContentHandsontableHelper.createNew("ModbusProtocolReportUnitContentConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','显示级别','数据顺序','报表曲线','','','','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,singleWellReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,singleWellReportTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'},"
						+"{data:'remark'}"
						+"]";
				singleWellReportTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				singleWellReportTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				singleWellReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				singleWellReportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType: deviceType,
			reportType: 0,
			unitId: unitId,
			classes: classes
        }
	});
};

var SingleWellReportTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var singleWellReportTemplateContentHandsontableHelper = {};
	        singleWellReportTemplateContentHandsontableHelper.hot1 = '';
	        singleWellReportTemplateContentHandsontableHelper.divid = divid;
	        singleWellReportTemplateContentHandsontableHelper.validresult=true;//数据校验
	        singleWellReportTemplateContentHandsontableHelper.colHeaders=[];
	        singleWellReportTemplateContentHandsontableHelper.columns=[];
	        singleWellReportTemplateContentHandsontableHelper.AllData=[];
	        
	        singleWellReportTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        singleWellReportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        singleWellReportTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+singleWellReportTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+singleWellReportTemplateContentHandsontableHelper.divid);
	        	singleWellReportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [7,8,9,10],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,30,140,80,60,60,85,85,],
	                columns:singleWellReportTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:singleWellReportTemplateContentHandsontableHelper.colHeaders,//显示列头
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
	                    var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	                	if(reportUnitTreeSelectedRow!=''){
	                		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
	                		if(selectedItem.data.classes==0){
	                			cellProperties.readOnly = true;
	                		}else{
	                			if (visualColIndex >=1 && visualColIndex<=3) {
	    							cellProperties.readOnly = true;
	    		                }else if(visualColIndex==6){
	    		                	cellProperties.renderer = singleWellReportTemplateContentHandsontableHelper.addCurveBg;
	    		                }
	                		}
	                	}
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(singleWellReportTemplateContentHandsontableHelper!=null && singleWellReportTemplateContentHandsontableHelper.hot!=undefined){
	                		var row1=singleWellReportTemplateContentHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==6)){
		                		var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                		if(reportUnitTreeSelectedRow!=''){
		                			var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                			if(selectedItem.data.classes==1){
		                				var CurveConfigWindow=Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");
		                				
		                				Ext.getCmp("curveConfigSelectedTableType_Id").setValue(21);//单井报表内容表
		                				Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
		                				Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);
		                				
		                				CurveConfigWindow.show();
		                				
		                				var curveConfig=null;
		                				if(column==6 && isNotVal(row1[7])){
		                					curveConfig=row1[7];
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
	                	if(singleWellReportTemplateContentHandsontableHelper!=null&&singleWellReportTemplateContentHandsontableHelper.hot!=''&&singleWellReportTemplateContentHandsontableHelper.hot!=undefined && singleWellReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		if(coords.col==2){
	                			var remark=singleWellReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,10);
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
	        singleWellReportTemplateContentHandsontableHelper.saveData = function () {}
	        singleWellReportTemplateContentHandsontableHelper.clearContainer = function () {
	        	singleWellReportTemplateContentHandsontableHelper.AllData = [];
	        }
	        return singleWellReportTemplateContentHandsontableHelper;
	    }
};

function CreateProtocolReportUnitPropertiesInfoTable(data){
	var root=[];
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
	        		colWidths: [2,5,5],
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
	                    if (visualColIndex ==0 || visualColIndex ==1) {
							cellProperties.readOnly = true;
							cellProperties.renderer = reportUnitPropertiesHandsontableHelper.addBoldBg;
		                }
	                    if(reportUnitPropertiesHandsontableHelper.classes===1){
	                    	if(visualColIndex === 2 && visualRowIndex===0){
		                    	this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_NotNull(val, callback, row, col, reportUnitPropertiesHandsontableHelper);
		                    	}
		                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    	this.type = 'dropdown';
		                    	this.source = ['抽油机井','螺杆泵井'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }else if(visualColIndex === 2 && visualRowIndex===3){
		                    	this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, reportUnitPropertiesHandsontableHelper);
		                    	}
		                    }
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

function CreateProductionReportTemplateInfoTable(name,deviceType,code){
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
			deviceType:deviceType,
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

function CreateproductionReportTotalItemsInfoTable(deviceType,unitId,unitName,classes){
	Ext.getCmp("ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
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
				var colHeaders="['','序号','名称','单位','显示级别','数据顺序','求和','求平均','报表曲线','曲线统计类型','','','','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
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
			deviceType: deviceType,
			reportType: 1,
			unitId: unitId,
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
	                    columns: [10,11,12,13],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,30,140,80,60,60,30,45,85,70],
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
	                    var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	                	if(reportUnitTreeSelectedRow!=''){
	                		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
	                		if(selectedItem.data.classes==0){
	                			cellProperties.readOnly = true;
	                		}else{
	                			if (visualColIndex >=1 && visualColIndex<=3) {
	    							cellProperties.readOnly = true;
	    		                }else if(visualColIndex==8){
	    		                	cellProperties.renderer = productionReportTemplateContentHandsontableHelper.addCurveBg;
	    		                }
	                		}
	                	}
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(productionReportTemplateContentHandsontableHelper!=null && productionReportTemplateContentHandsontableHelper.hot!=undefined){
	                		var row1=productionReportTemplateContentHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==8)){
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
		                				if(column==8 && isNotVal(row1[10])){
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
	                	if(productionReportTemplateContentHandsontableHelper!=null&&productionReportTemplateContentHandsontableHelper.hot!=''&&productionReportTemplateContentHandsontableHelper.hot!=undefined && productionReportTemplateContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		if(coords.col==2){
	                			var remark=productionReportTemplateContentHandsontableHelper.hot.getDataAtCell(coords.row,13);
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
			
			reportUnitProperties.singlewellReportTemplate=selectedItem.data.singlewellReportTemplate;
			reportUnitProperties.productionReportTemplate=selectedItem.data.productionReportTemplate;
			
			var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportTemplateTabPanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
				var templateSelection= Ext.getCmp("ReportUnitSingleWellReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
				if(templateSelection.length>0){
					reportUnitProperties.singlewellReportTemplate=templateSelection[0].data.templateCode;
				}
			}else if(activeId=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
				var templateSelection= Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id").getSelectionModel().getSelection();
				if(templateSelection.length>0){
					reportUnitProperties.productionReportTemplate=templateSelection[0].data.templateCode;
				}
			}
			
			
			reportUnitProperties.deviceType=(propertiesData[1][2]=="抽油机井"?0:1);
			reportUnitProperties.sort=propertiesData[2][2];
		}
		if(selectedItem.data.classes==1){//保存单元
			SaveModbusProtocolReportUnitData(reportUnitProperties);
			grantReportTotalCalItemsPermission();
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

var grantReportTotalCalItemsPermission = function () {
	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	var reportType=0;
	var calItemsData = null;
	var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportTemplateTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
		if (singleWellReportTemplateContentHandsontableHelper == null || reportUnitTreeSelectedRow=='') {
	        return false;
	    }
		reportType=0;
		calItemsData = singleWellReportTemplateContentHandsontableHelper.hot.getData();
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
            	
        		item.itemShowLevel = calItemsData[index][4];
        		item.itemSort = calItemsData[index][5];
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(calItemsData[index][6]) && isNotVal(calItemsData[index][7])){
    				reportCurveConfig=calItemsData[index][7];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = calItemsData[index][8];
        		item.dataType = calItemsData[index][9];
        	}else if(reportType==1){
        		item.itemName = calItemsData[index][2];
            	
        		item.itemShowLevel = calItemsData[index][4];
        		item.itemSort = calItemsData[index][5];
        		
        		item.sumSign='0';
        		if((calItemsData[index][6]+'')==='true'){
        			item.sumSign='1';
        		}
        		
        		item.averageSign='0';
        		if((calItemsData[index][7]+'')==='true'){
        			item.averageSign='1';
        		}
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(calItemsData[index][8]) && isNotVal(calItemsData[index][10])){
    				reportCurveConfig=calItemsData[index][10];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
        		
        		item.curveStatType = calItemsData[index][9];
        		if(calItemsData[index][9]=='合计'){
        			item.curveStatType='1';
        		}else if(calItemsData[index][9]=='平均'){
        			item.curveStatType='2';
        		}else if(calItemsData[index][9]=='最大值'){
        			item.curveStatType='3';
        		}else if(calItemsData[index][9]=='最小值'){
        			item.curveStatType='4';
        		}
        		
            	
        		item.itemCode = calItemsData[index][11];
        		item.dataType = calItemsData[index][12]+"";
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