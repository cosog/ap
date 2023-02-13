var reportInstanceTemplateHandsontableHelper=null;
var reportInstanceTemplateContentHandsontableHelper=null;
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
                    border: true,
                    header: false,
                    split: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'报表实例列表',
                    	layout: 'fit',
                    	id:"ModbusProtocolReportInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
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
                	border: true,
                	region: 'center',
                	layout: "border",
                	items: [{
                		region: 'center',
                		layout: "border",
                		items: [{
                    		region: 'center',
                    		title:'报表模板',
                    		id:"ModbusProtocolReportInstanceTemplateTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ModbusProtocolReportInstanceTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstanceTemplateTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (reportInstanceTemplateHandsontableHelper != null && reportInstanceTemplateHandsontableHelper.hot != null && reportInstanceTemplateHandsontableHelper.hot != undefined) {
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                    	reportInstanceTemplateHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                    }
                                }
                            }
                    	},{
                    		region: 'south',
                        	height:'50%',
                        	title:'报表内容',
                        	collapsible: true,
                            split: true,
                        	layout: 'fit',
                        	id:"ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ModbusProtocolReportInstanceContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstanceContentConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if (reportInstanceTemplateContentHandsontableHelper != null && reportInstanceTemplateContentHandsontableHelper.hot != null && reportInstanceTemplateContentHandsontableHelper.hot != undefined) {
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		reportInstanceTemplateContentHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                    }
                                }
                            }
                    	}]
                	}] 
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateReportInstanceTemplateInfoTable(name,classes,code){
	Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").setTitle('实例报表模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(reportInstanceTemplateHandsontableHelper!=null){
				if(reportInstanceTemplateHandsontableHelper.hot!=undefined){
					reportInstanceTemplateHandsontableHelper.hot.destroy();
				}
				reportInstanceTemplateHandsontableHelper=null;
			}
			
			if(reportInstanceTemplateHandsontableHelper==null || reportInstanceTemplateHandsontableHelper.hot==undefined){
				reportInstanceTemplateHandsontableHelper = ReportInstanceTemplateHandsontableHelper.createNew("ModbusProtocolReportInstanceTemplateTableInfoDiv_id","ModbusProtocolReportInstanceTemplateTableInfoContainer",result);
				reportInstanceTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportInstanceTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			name:name,
			classes:classes,
			code:code
        }
	});
}

var ReportInstanceTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var reportInstanceTemplateHandsontableHelper = {};
	        reportInstanceTemplateHandsontableHelper.templateData=templateData;
	        reportInstanceTemplateHandsontableHelper.get_data = {};
	        reportInstanceTemplateHandsontableHelper.data=[];
	        reportInstanceTemplateHandsontableHelper.hot = '';
	        reportInstanceTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        reportInstanceTemplateHandsontableHelper.initData=function(){
	        	reportInstanceTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<reportInstanceTemplateHandsontableHelper.templateData.header.length;i++){
		        	reportInstanceTemplateHandsontableHelper.data.push(reportInstanceTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        reportInstanceTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(reportInstanceTemplateHandsontableHelper!=null && reportInstanceTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<reportInstanceTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = reportInstanceTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        reportInstanceTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			reportInstanceTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			reportInstanceTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        reportInstanceTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        reportInstanceTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        reportInstanceTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        reportInstanceTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        reportInstanceTemplateHandsontableHelper.createTable = function () {
	            reportInstanceTemplateHandsontableHelper.container.innerHTML = "";
	            reportInstanceTemplateHandsontableHelper.hot = new Handsontable(reportInstanceTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: reportInstanceTemplateHandsontableHelper.data,
	                fixedRowsTop:reportInstanceTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: reportInstanceTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: reportInstanceTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: reportInstanceTemplateHandsontableHelper.templateData.columnWidths,
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
	                mergeCells: reportInstanceTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = reportInstanceTemplateHandsontableHelper.addStyle;
//	                    if (visualRowIndex <= 2 && visualRowIndex >= 1) {
//	                        cellProperties.renderer = reportInstanceTemplateHandsontableHelper.addBoldBg;
//	                    }
//						if (visualRowIndex < 1 ) {
//	                       cellProperties.renderer = reportInstanceTemplateHandsontableHelper.addSizeBg;
//	                    }
//						if (visualColIndex === 26&&visualRowIndex>2&&visualRowIndex<reportInstanceTemplateHandsontableHelper.last_index) {
//							cellProperties.readOnly = false;
//		                }
	                    return cellProperties;
	                },
	                afterChange:function(changes, source){}
	            });
	        }
	        reportInstanceTemplateHandsontableHelper.getData = function (data) {
//	            reportInstanceTemplateHandsontableHelper.get_data = data;
//	            reportInstanceTemplateHandsontableHelper.editable = +data.Editable;
//	            var _daily = data.totalRoot;
//	            reportInstanceTemplateHandsontableHelper.sum = _daily.length;
//	            reportInstanceTemplateHandsontableHelper.updateArray();
//	            _daily.forEach(function (_day, index) {
//
//	            	if(_day.id=="合计" || _day.id=="平均"){
//	            		reportInstanceTemplateHandsontableHelper.my_data[index + 3][0] = _day.id;
//	            	}else{
//	            		reportInstanceTemplateHandsontableHelper.my_data[index + 3][0] = index+1;
//	            	}
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][1] = _day.wellName;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][2] = _day.calculateDate;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][3] = _day.commTime;
//	                var commRange=_day.commRange;
//	                if(commRange.length>12){
//	                	commRange=commRange.substring(0, 11)+"...";
//	                }
//	                var runRange=_day.runRange;
//	                if(runRange.length>12){
//	                	runRange=runRange.substring(0, 11)+"...";
//	                }
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][4] = commRange;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][5] = _day.commTimeEfficiency;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][6] = _day.runTime;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][7] = runRange;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][8] = _day.runTimeEfficiency;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][9] = _day.resultName;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][10] = _day.optimizationSuggestion;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][11] = _day.liquidProduction;
//					reportInstanceTemplateHandsontableHelper.my_data[index + 3][12] = _day.oilProduction;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][13] = _day.waterProduction;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][14] = _day.waterCut;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][15] = _day.fullnesscoEfficient;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][16] = _day.wattDegreeBalance;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][17] = _day.iDegreeBalance;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][18] = _day.deltaRadius;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][19] = _day.systemEfficiency;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][20] = _day.surfaceSystemEfficiency;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][21] = _day.welldownSystemEfficiency;
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][22] = _day.energyPer100mLift;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][23] = _day.todayKWattH;
//	                
//	                reportInstanceTemplateHandsontableHelper.my_data[index + 3][24] = _day.remark;
//	            })
//
//	            var _total = data.totalCount;
//	            reportInstanceTemplateHandsontableHelper.last_index = _daily.length + 3;
	        }

	        var init = function () {
	        	reportInstanceTemplateHandsontableHelper.initData();
	        }

	        init();
	        return reportInstanceTemplateHandsontableHelper;
	    }
	};

function CreateReportInstanceTotalItemsInfoTable(deviceType,unitCode,unitName,classes){
	Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportInstanceTotalCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
//			if(classes==0){
//				Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").setTitle('报表内容');
//			}else{
//				Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").setTitle('报表内容：'+unitName);
//			}
			if(reportInstanceTemplateContentHandsontableHelper==null || reportInstanceTemplateContentHandsontableHelper.hot==undefined){
				reportInstanceTemplateContentHandsontableHelper = ReportInstanceTemplateContentHandsontableHelper.createNew("ModbusProtocolReportInstanceContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','显示顺序','报表曲线顺序','报表曲线颜色','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportInstanceTemplateContentHandsontableHelper);}}," 
						+"{data:'reportCurveColor'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				reportInstanceTemplateContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportInstanceTemplateContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportInstanceTemplateContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportInstanceTemplateContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolReportInstanceContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType:deviceType,
			unitCode:unitCode,
			classes:classes
        }
	});
};

var ReportInstanceTemplateContentHandsontableHelper = {
		createNew: function (divid) {
	        var reportInstanceTemplateContentHandsontableHelper = {};
	        reportInstanceTemplateContentHandsontableHelper.hot1 = '';
	        reportInstanceTemplateContentHandsontableHelper.divid = divid;
	        reportInstanceTemplateContentHandsontableHelper.validresult=true;//数据校验
	        reportInstanceTemplateContentHandsontableHelper.colHeaders=[];
	        reportInstanceTemplateContentHandsontableHelper.columns=[];
	        reportInstanceTemplateContentHandsontableHelper.AllData=[];
	        
	        reportInstanceTemplateContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        reportInstanceTemplateContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        reportInstanceTemplateContentHandsontableHelper.createTable = function (data) {
	        	$('#'+reportInstanceTemplateContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportInstanceTemplateContentHandsontableHelper.divid);
	        	reportInstanceTemplateContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [7,8],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,85,85],
	                columns:reportInstanceTemplateContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportInstanceTemplateContentHandsontableHelper.colHeaders,//显示列头
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
		                	cellProperties.renderer = reportInstanceTemplateContentHandsontableHelper.addCurveBg;
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
	        reportInstanceTemplateContentHandsontableHelper.saveData = function () {}
	        reportInstanceTemplateContentHandsontableHelper.clearContainer = function () {
	        	reportInstanceTemplateContentHandsontableHelper.AllData = [];
	        }
	        return reportInstanceTemplateContentHandsontableHelper;
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
		protocolReportInstancePropertiesHandsontableHelper.createTable(root);
	}else{
		protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
		protocolReportInstancePropertiesHandsontableHelper.hot.loadData(root);
	}
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
	        		colWidths: [2,5,5],
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