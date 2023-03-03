var reportTemplateHandsontableHelper=null;
var reportTemplateContentHandsontableHelper=null;
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
                        hidden: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolReportUnitPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                    }]
                },{
                	border: true,
                	region: 'center',
                	xtype: 'tabpanel',
                	id:"ModbusProtocolReportUnitReportTemplateTabPanel_Id",
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	title:'单井报表',
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
                        		title:'报表模板',
                        		id:"ModbusProtocolReportUnitTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ModbusProtocolReportUnitTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (reportTemplateHandsontableHelper != null && reportTemplateHandsontableHelper.hot != null && reportTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                        	reportTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'报表内容配置',
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	border: false,
                            	id:"ModbusProtocolReportUnitContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ModbusProtocolReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (reportTemplateContentHandsontableHelper != null && reportTemplateContentHandsontableHelper.hot != null && reportTemplateContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		reportTemplateContentHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}]
                    	}]
                    },{
                    	title:'生产报表',
                    	id:'ModbusProtocolReportUnitProductionReportTemplatePanel_Id',
                    	layout: "border",
                    	border: false,
                    	items: [{
                    		region: 'west',
                        	width:'16%',
                        	layout: 'fit',
                            border: false,
                            title:'报表模板列表',
                            split: true,
                            collapsible: true
                    	},{
                    		region: 'center',
                    		layout: "border",
                    		border: false,
                    		items: [{
                        		region: 'center',
                        		title:'报表模板',
                        		id:"ModbusProtocolReportUnitProductionTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ModbusProtocolReportUnitProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitProductionTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
//                                    	if (reportTemplateHandsontableHelper != null && reportTemplateHandsontableHelper.hot != null && reportTemplateHandsontableHelper.hot != undefined) {
//                                    		var newWidth=width;
//                                    		var newHeight=height;
//                                    		var header=thisPanel.getHeader();
//                                    		if(header){
//                                    			newHeight=newHeight-header.lastBox.height-2;
//                                    		}
//                                        	reportTemplateHandsontableHelper.hot.updateSettings({
//                                    			width:newWidth,
//                                    			height:newHeight
//                                    		});
//                                        }
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'报表内容配置',
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	border: false,
                            	id:"ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ModbusProtocolProductionReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolProductionReportUnitContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
//                                    	if (reportTemplateContentHandsontableHelper != null && reportTemplateContentHandsontableHelper.hot != null && reportTemplateContentHandsontableHelper.hot != undefined) {
//                                    		var newWidth=width;
//                                    		var newHeight=height;
//                                    		var header=thisPanel.getHeader();
//                                    		if(header){
//                                    			newHeight=newHeight-header.lastBox.height-2;
//                                    		}
//                                    		reportTemplateContentHandsontableHelper.hot.updateSettings({
//                                    			width:newWidth,
//                                    			height:newHeight
//                                    		});
//                                        }
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                        	if(newCard.id=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
                        		
                        	}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateReportTemplateInfoTable(name,classes,code){
	Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").setTitle('报表模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(reportTemplateHandsontableHelper!=null){
				if(reportTemplateHandsontableHelper.hot!=undefined){
					reportTemplateHandsontableHelper.hot.destroy();
				}
				reportTemplateHandsontableHelper=null;
			}
			
			if(reportTemplateHandsontableHelper==null || reportTemplateHandsontableHelper.hot==undefined){
				reportTemplateHandsontableHelper = ReportTemplateHandsontableHelper.createNew("ModbusProtocolReportUnitTemplateTableInfoDiv_id","ModbusProtocolReportUnitTemplateTableInfoContainer",result);
				reportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			name:name,
			classes:classes,
			code:code
        }
	});
}

var ReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var reportTemplateHandsontableHelper = {};
	        reportTemplateHandsontableHelper.templateData=templateData;
	        reportTemplateHandsontableHelper.get_data = {};
	        reportTemplateHandsontableHelper.data=[];
	        reportTemplateHandsontableHelper.hot = '';
	        reportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        reportTemplateHandsontableHelper.initData=function(){
	        	reportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<reportTemplateHandsontableHelper.templateData.header.length;i++){
		        	reportTemplateHandsontableHelper.data.push(reportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        reportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(reportTemplateHandsontableHelper!=null && reportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<reportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(reportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = reportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        reportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			reportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			reportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        reportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        reportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        reportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        reportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        reportTemplateHandsontableHelper.createTable = function () {
	            reportTemplateHandsontableHelper.container.innerHTML = "";
	            reportTemplateHandsontableHelper.hot = new Handsontable(reportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: reportTemplateHandsontableHelper.data,
	                fixedRowsTop:reportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: reportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: reportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: reportTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: reportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = reportTemplateHandsontableHelper.addStyle;
//	                    if (visualRowIndex <= 2 && visualRowIndex >= 1) {
//	                        cellProperties.renderer = reportTemplateHandsontableHelper.addBoldBg;
//	                    }
//						if (visualRowIndex < 1 ) {
//	                       cellProperties.renderer = reportTemplateHandsontableHelper.addSizeBg;
//	                    }
//						if (visualColIndex === 26&&visualRowIndex>2&&visualRowIndex<reportTemplateHandsontableHelper.last_index) {
//							cellProperties.readOnly = false;
//		                }
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        reportTemplateHandsontableHelper.getData = function (data) {
//	            reportTemplateHandsontableHelper.get_data = data;
//	            reportTemplateHandsontableHelper.editable = +data.Editable;
//	            var _daily = data.totalRoot;
//	            reportTemplateHandsontableHelper.sum = _daily.length;
//	            reportTemplateHandsontableHelper.updateArray();
//	            _daily.forEach(function (_day, index) {
//
//	            	if(_day.id=="合计" || _day.id=="平均"){
//	            		reportTemplateHandsontableHelper.my_data[index + 3][0] = _day.id;
//	            	}else{
//	            		reportTemplateHandsontableHelper.my_data[index + 3][0] = index+1;
//	            	}
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][1] = _day.wellName;
//	                reportTemplateHandsontableHelper.my_data[index + 3][2] = _day.calculateDate;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][3] = _day.commTime;
//	                var commRange=_day.commRange;
//	                if(commRange.length>12){
//	                	commRange=commRange.substring(0, 11)+"...";
//	                }
//	                var runRange=_day.runRange;
//	                if(runRange.length>12){
//	                	runRange=runRange.substring(0, 11)+"...";
//	                }
//	                reportTemplateHandsontableHelper.my_data[index + 3][4] = commRange;
//	                reportTemplateHandsontableHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][6] = _day.runTime;
//	                reportTemplateHandsontableHelper.my_data[index + 3][7] = runRange;
//	                reportTemplateHandsontableHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][9] = _day.resultName;
//	                reportTemplateHandsontableHelper.my_data[index + 3][10] = _day.optimizationSuggestion;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][11] = _day.liquidProduction;
//					reportTemplateHandsontableHelper.my_data[index + 3][12] = _day.oilProduction;
//	                reportTemplateHandsontableHelper.my_data[index + 3][13] = _day.waterProduction;
//	                reportTemplateHandsontableHelper.my_data[index + 3][14] = _day.waterCut;
//	                reportTemplateHandsontableHelper.my_data[index + 3][15] = _day.fullnesscoEfficient;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][16] = _day.wattDegreeBalance;
//	                reportTemplateHandsontableHelper.my_data[index + 3][17] = _day.iDegreeBalance;
//	                reportTemplateHandsontableHelper.my_data[index + 3][18] = _day.deltaRadius;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][19] = _day.systemEfficiency;
//	                reportTemplateHandsontableHelper.my_data[index + 3][20] = _day.surfaceSystemEfficiency;
//	                reportTemplateHandsontableHelper.my_data[index + 3][21] = _day.welldownSystemEfficiency;
//	                reportTemplateHandsontableHelper.my_data[index + 3][22] = _day.energyPer100mLift;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][23] = _day.todayKWattH;
//	                
//	                reportTemplateHandsontableHelper.my_data[index + 3][24] = _day.remark;
//	            })
//
//	            var _total = data.totalCount;
//	            reportTemplateHandsontableHelper.last_index = _daily.length + 3;
	        }

	        var init = function () {
	        	reportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return reportTemplateHandsontableHelper;
	    }
	};

function CreateReportTotalItemsInfoTable(deviceType,unitCode,unitName,classes){
	Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==0){
				Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").setTitle('报表内容配置');
			}else{
				Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").setTitle('报表内容配置：'+unitName);
			}
			if(reportTemplateContentHandsontableHelper==null || reportTemplateContentHandsontableHelper.hot==undefined){
				reportTemplateContentHandsontableHelper = ReportTemplateContentHandsontableHelper.createNew("ModbusProtocolReportUnitContentConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','显示级别','显示顺序','报表曲线顺序','报表曲线颜色','','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurveColor'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				reportTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportUnitContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType:deviceType,
			unitCode:unitCode,
			classes:classes
        }
	});
};

var ReportTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var reportTemplateContentHandsontableHelper = {};
	        reportTemplateContentHandsontableHelper.hot1 = '';
	        reportTemplateContentHandsontableHelper.divid = divid;
	        reportTemplateContentHandsontableHelper.validresult=true;//数据校验
	        reportTemplateContentHandsontableHelper.colHeaders=[];
	        reportTemplateContentHandsontableHelper.columns=[];
	        reportTemplateContentHandsontableHelper.AllData=[];
	        
	        reportTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        reportTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        reportTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportTemplateContentHandsontableHelper.divid);
	        	reportTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [8,9],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,30,140,80,60,60,85,85,],
	                columns:reportTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportTemplateContentHandsontableHelper.colHeaders,//显示列头
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
	    		                }else if(visualColIndex==7){
	    		                	cellProperties.renderer = reportTemplateContentHandsontableHelper.addCurveBg;
	    		                }
	                		}
	                	}
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(reportTemplateContentHandsontableHelper!=null && reportTemplateContentHandsontableHelper.hot!=undefined){
	                		var row1=reportTemplateContentHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==7)){
		                		var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                		if(reportUnitTreeSelectedRow!=''){
		                			var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                			if(selectedItem.data.classes==1){
		                				var CurveColorSelectWindow=Ext.create("AP.view.acquisitionUnit.CurveColorSelectWindow");
		                				Ext.getCmp("curveColorSelectedTableType_Id").setValue(21);//汇总计算项表
		                				Ext.getCmp("curveColorSelectedRow_Id").setValue(row);
		                				Ext.getCmp("curveColorSelectedCol_Id").setValue(column);
		                				CurveColorSelectWindow.show();
		                				var value=row1[column];
		                				if(value==null||value==''){
		                					value='ff0000';
		                				}
		                				Ext.getCmp('CurveColorSelectWindowColor_id').setValue(value);
	                		        	var BackgroundColor=Ext.getCmp('CurveColorSelectWindowColor_id').color;
	                		        	BackgroundColor.a=1;
	                		        	Ext.getCmp('CurveColorSelectWindowColor_id').setColor(BackgroundColor);
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
	        reportTemplateContentHandsontableHelper.saveData = function () {}
	        reportTemplateContentHandsontableHelper.clearContainer = function () {
	        	reportTemplateContentHandsontableHelper.AllData = [];
	        }
	        return reportTemplateContentHandsontableHelper;
	    }
};

function SaveReportUnitData(){
	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	if(reportUnitTreeSelectedRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
//		var propertiesData=protocolReportUnitPropertiesHandsontableHelper.hot.getData();
//		var reportUnitProperties={};
//		if(selectedItem.data.classes==1){//选中的是单元
//			reportUnitProperties.classes=selectedItem.data.classes;
//			reportUnitProperties.id=selectedItem.data.id;
//			reportUnitProperties.unitCode=selectedItem.data.code;
//			reportUnitProperties.unitName=propertiesData[0][2];
//			reportUnitProperties.acqUnitId=selectedItem.data.acqUnitId;
//			reportUnitProperties.acqUnitName=propertiesData[1][2];
//			reportUnitProperties.remark=propertiesData[2][2];
//		}
		if(selectedItem.data.classes==1){//保存单元
//			var reportUnitSaveData={};
//			reportUnitSaveData.updatelist=[];
//			reportUnitSaveData.updatelist.push(reportUnitProperties);
//			saveReportUnitConfigData(reportUnitSaveData,selectedItem.data.protocol,selectedItem.parentNode.data.deviceType);
			grantReportTotalCalItemsPermission();
		}
	}
};

var grantReportTotalCalItemsPermission = function () {
	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
	if (reportTemplateContentHandsontableHelper == null ||reportUnitTreeSelectedRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
    var calItemsData = reportTemplateContentHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantTotalCalItemsToReportUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    
    var unitCode = selectedItem.data.code;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(calItemsData, function (name, index, countriesItSelf) {
        if ((calItemsData[index][0]+'')==='true') {
        	var itemName = calItemsData[index][2];
        	
        	var itemShowLevel = calItemsData[index][4];
        	var itemSort = calItemsData[index][5];
        	var reportCurve=calItemsData[index][6];
        	var reportCurveColor=calItemsData[index][7];
        	
        	var itemCode = calItemsData[index][8];
        	var dataType = calItemsData[index][9];
        	
            addjson.push(itemCode);
            addItemSort.push(itemSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + ":"
            + itemCode+ ":"
            + itemSort+ ":"
            + itemShowLevel+ ":" 
            + reportCurve+ ":" 
            + reportCurveColor+ ":" 
            + dataType + ":" 
            + matrix_value+ "|";
        }
    });
    matrixData = matrixData.substring(0, matrixData.length - 1);
    var addparams = "" + addjson.join(",");
    var addSortParams = "" + addItemSort.join(",");
    var matrixCodes_ = "" + matrixData;
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        params: {
            params: addparams,
            sorts: addSortParams,
            unitCode: unitCode,
            matrixCodes: matrixCodes_
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