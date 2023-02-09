var reportTemplateHandsontableHelper=null;
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
//        				SaveModbusProtocolReportUnitConfigTreeData();
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
                    	title:'报表单元配置',
                    	layout: 'fit',
                    	id:"ModbusProtocolReportUnitConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
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
                	layout: "border",
                	items: [{
                		region: 'center',
                		layout: "border",
                		items: [{
                    		region: 'center',
                    		title:'报表模板',
                    		id:"ModbusProtocolReportUnitTemplateTableInfoPanel_Id",
                            layout: 'fit',
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
                        	id:"ModbusProtocolReportUnitContentConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ModbusProtocolReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportUnitContentConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	
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

function CreateReportTemplateInfoTable(name,classes,code){
	Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportTemplateData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").setTitle('报表模板：'+name);
			var result =  Ext.JSON.decode(response.responseText);
			
			reportTemplateHandsontableHelper = ReportTemplateHandsontableHelper.createNew("ModbusProtocolReportUnitTemplateTableInfoDiv_id","ModbusProtocolReportUnitTemplateTableInfoContainer",result);
//			reportTemplateHandsontableHelper.getData(result);
			reportTemplateHandsontableHelper.createTable();
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
	        
	        for(var i=0;i<reportTemplateHandsontableHelper.templateData.header.length;i++){
	        	reportTemplateHandsontableHelper.data.push(reportTemplateHandsontableHelper.templateData.header[i].title);
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
					rowHeaders: true, //显示行头
					colHeaders: true,
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
	        }

	        init();
	        return reportTemplateHandsontableHelper;
	    }
	};