var reportUnitContentConfigHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ReportUnitContentConfigWindow", {
    extend: 'Ext.window.Window',
    id:'ReportUnitContentConfigWindow_Id',
    alias: 'widget.reportUnitContentConfigWindow',
    layout: 'fit',
    title:'报表内容配置',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1300,
    minWidth: 1200,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
                xtype: "hidden",
                fieldLabel: '报表单元Id',
                id: 'ReportUnitContentConfig_UnitId',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '报表类型',
                id: 'ReportUnitContentConfig_ReportType',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '计算类型',
                id: 'ReportUnitContentConfig_CalculateType',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '所选行',
                id: 'ReportUnitContentConfig_SelectedRow',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '所选列',
                id: 'ReportUnitContentConfig_SelectedCol',
                value: ''
            },{
                xtype: 'button',
                text: '重新加载',
//                iconCls: 'save',
                handler: function (v, o) {
                	CreateReportUnitContentConfigTable();
                }
            },'->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var unitId=Ext.getCmp("ReportUnitContentConfig_UnitId").getValue();
                	var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
                    var calculateType=Ext.getCmp("ReportUnitContentConfig_CalculateType").getValue();
                    var row=Ext.getCmp("ReportUnitContentConfig_SelectedRow").getValue();
                    var col=Ext.getCmp("ReportUnitContentConfig_SelectedCol").getValue();
                    var sort=parseInt(row)+1;
                    var itemsConfigData = reportUnitContentConfigHandsontableHelper.hot.getData();
                	
                	grantReportUnitContentItemsPermission(unitId,reportType,calculateType,sort,itemsConfigData);
                }
            }],
            layout: 'border',
            items: [{
            	region: 'center',
            	id:'ReportUnitContentConfigPanel_Id',
            	html: '<div id="ReportUnitContentConfigDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(reportUnitContentConfigHandsontableHelper!=null&&reportUnitContentConfigHandsontableHelper.hot!=null&&reportUnitContentConfigHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		reportUnitContentConfigHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		CreateReportUnitContentConfigTable();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(reportUnitContentConfigHandsontableHelper!=null){
    					if(reportUnitContentConfigHandsontableHelper.hot!=undefined){
    						reportUnitContentConfigHandsontableHelper.hot.destroy();
    					}
    					reportUnitContentConfigHandsontableHelper=null;
    				}
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function CreateReportUnitContentConfigTable() {
	var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	if(reportType==0){
		CreateSingleWellRangeReportContentConfigTable();
	}
}

function CreateSingleWellRangeReportContentConfigTable() {
	if(reportUnitContentConfigHandsontableHelper!=null){
		if (reportUnitContentConfigHandsontableHelper.hot != undefined) {
			reportUnitContentConfigHandsontableHelper.hot.destroy();
		}
		reportUnitContentConfigHandsontableHelper = null;
	}
	
	var unitId=Ext.getCmp("ReportUnitContentConfig_UnitId").getValue();
	var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
    var calculateType=Ext.getCmp("ReportUnitContentConfig_CalculateType").getValue();
    var row=Ext.getCmp("ReportUnitContentConfig_SelectedRow").getValue();
    var col=Ext.getCmp("ReportUnitContentConfig_SelectedCol").getValue();
    
    var sort=parseInt(row)+1;
	
	Ext.getCmp("ReportUnitContentConfigPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitContentConfigItemsData',
		success:function(response) {
			Ext.getCmp("ReportUnitContentConfigPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(reportUnitContentConfigHandsontableHelper==null || reportUnitContentConfigHandsontableHelper.hot==undefined){
				reportUnitContentConfigHandsontableHelper = ReportUnitContentConfigHandsontableHelper.createNew("ReportUnitContentConfigDiv_Id");
				var colHeaders="['','序号','名称','单位','数据来源','统计方式','显示级别','小数位数','报表曲线','','','','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'},"
						+"{data:'remark'}"
						+"]";
				reportUnitContentConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				reportUnitContentConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				reportUnitContentConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportUnitContentConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ReportUnitContentConfigPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			unitId: unitId,
			reportType: reportType,
			calculateType: calculateType,
			row: row,
			col: col
        }
	});
};

var ReportUnitContentConfigHandsontableHelper = {
		createNew: function (divid) {
	        var reportUnitContentConfigHandsontableHelper = {};
	        reportUnitContentConfigHandsontableHelper.hot1 = '';
	        reportUnitContentConfigHandsontableHelper.divid = divid;
	        reportUnitContentConfigHandsontableHelper.validresult=true;//数据校验
	        reportUnitContentConfigHandsontableHelper.colHeaders=[];
	        reportUnitContentConfigHandsontableHelper.columns=[];
	        reportUnitContentConfigHandsontableHelper.AllData=[];
	        
	        reportUnitContentConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        reportUnitContentConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        reportUnitContentConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+reportUnitContentConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportUnitContentConfigHandsontableHelper.divid);
	        	reportUnitContentConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10,11,12],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,30,150,80,60,60,60,60,85,85],
	                columns:reportUnitContentConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportUnitContentConfigHandsontableHelper.colHeaders,//显示列头
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
		    		                }else if(visualColIndex==8){
		    		                	cellProperties.renderer = reportUnitContentConfigHandsontableHelper.addCurveBg;
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(reportUnitContentConfigHandsontableHelper!=null && reportUnitContentConfigHandsontableHelper.hot!=undefined){
	                		var row1=reportUnitContentConfigHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==8)){
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
		                				if(column==8 && isNotVal(row1[9])){
		                					curveConfig=row1[9];
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
	                	if(reportUnitContentConfigHandsontableHelper!=null&&reportUnitContentConfigHandsontableHelper.hot!=''&&reportUnitContentConfigHandsontableHelper.hot!=undefined && reportUnitContentConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		if(coords.col==2){
	                			var remark=reportUnitContentConfigHandsontableHelper.hot.getDataAtCell(coords.row,12);
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
	                	if(row==row2 && column==column2 && column==0){
	                		var selectedRow=row;
		                	var selectedCol=column;
		                	var checkboxColData=reportUnitContentConfigHandsontableHelper.hot.getDataAtCol(0);
		                	var rowdata = reportUnitContentConfigHandsontableHelper.hot.getDataAtRow(selectedRow);
		                	
		                	for(var i=0;i<checkboxColData.length;i++){
	                			if(i!=selectedRow&&checkboxColData[i]){
	                				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,0,false);
	                			}
	                		}
		                	
		                	if(rowdata[0]){
		                		reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(selectedRow,0,false);
		                	}else{
		                		reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(selectedRow,0,true);
		                	}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        reportUnitContentConfigHandsontableHelper.saveData = function () {}
	        reportUnitContentConfigHandsontableHelper.clearContainer = function () {
	        	reportUnitContentConfigHandsontableHelper.AllData = [];
	        }
	        return reportUnitContentConfigHandsontableHelper;
	    }
};