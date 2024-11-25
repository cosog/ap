var reportUnitContentConfigColInfoHandsontableHelper=null;
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
    width: 1400,
    minWidth: 1400,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
                xtype: "hidden",
                fieldLabel: 'classes',
                id: 'ReportUnitContentConfig_Classes',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '报表单元Id',
                id: 'ReportUnitContentConfig_UnitId',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '报表单元名称',
                id: 'ReportUnitContentConfig_UnitName',
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
                xtype: "hidden",
                fieldLabel: '所选模板',
                id: 'ReportUnitContentConfig_TemplateCode',
                value: ''
            },{
                xtype: 'button',
                text: '重新加载',
                hidden: true,
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
                    var itemsConfigData = reportUnitContentConfigColInfoHandsontableHelper.hot.getData();
                	
                	grantReportUnitContentItemsPermission(unitId,reportType,calculateType,sort,itemsConfigData);
                }
            }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'30%',
            	html: '<div id="ReportUnitContentConfigColInfoDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(reportUnitContentConfigColInfoHandsontableHelper!=null&&reportUnitContentConfigColInfoHandsontableHelper.hot!=null&&reportUnitContentConfigColInfoHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		reportUnitContentConfigColInfoHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		CreateReportUnitContentConfigColInfoTable();
                    	}
                    }
            	}
            },{
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
                	if(reportUnitContentConfigColInfoHandsontableHelper!=null){
    					if(reportUnitContentConfigColInfoHandsontableHelper.hot!=undefined){
    						reportUnitContentConfigColInfoHandsontableHelper.hot.destroy();
    					}
    					reportUnitContentConfigColInfoHandsontableHelper=null;
    				}
                	
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
		async: false,
		url:context + '/acquisitionUnitManagerController/getReportUnitContentConfigItemsData',
		success:function(response) {
			Ext.getCmp("ReportUnitContentConfigPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(reportUnitContentConfigHandsontableHelper==null || reportUnitContentConfigHandsontableHelper.hot==undefined){
				reportUnitContentConfigHandsontableHelper = ReportUnitContentConfigHandsontableHelper.createNew("ReportUnitContentConfigDiv_Id");
				var colHeaders=['','序号','字段','单位','数据来源','统计方式','显示级别','小数位数',loginUserLanguageResource.reportCurve,'','','',''];
				var columns=[
						{data:'checked',type:'checkbox'},
						{data:'id'},
						{data:'title'},
					 	{data:'unit'},
					 	{data:'dataSource'},
					 	{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']},
						{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
						{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
						{data:'reportCurveConfShowValue'},
						{data:'reportCurveConf'},
						{data:'code'},
						{data:'dataType'},
						{data:'remark'}
						];
				if(reportType==0){
					colHeaders=['','序号','字段','单位','数据来源','统计方式','显示级别','小数位数',loginUserLanguageResource.reportCurve,'','','',''];
					columns=[
							{data:'checked',type:'checkbox'},
							{data:'id'},
							{data:'title'},
						 	{data:'unit'},
						 	{data:'dataSource'},
						 	{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']},
							{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
							{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
							{data:'reportCurveConfShowValue'},
							{data:'reportCurveConf'},
							{data:'code'},
							{data:'dataType'},
							{data:'remark'}
							];
				}else if(reportType==1){
					colHeaders=['','序号','字段','单位','数据来源','统计方式','显示级别','小数位数','求和','求平均',loginUserLanguageResource.reportCurve,'曲线统计类型','','','',''];
					columns=[
							{data:'checked',type:'checkbox'},
							{data:'id'},
							{data:'title'},
						 	{data:'unit'},
						 	{data:'dataSource'},
						 	{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']},
							{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
							{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
							{data:'sumSign',type:'checkbox'},
		                    {data:'averageSign',type:'checkbox'},
							{data:'reportCurveConfShowValue'},
							{data:'curveStatType',type:'dropdown',strict:true,allowInvalid:false,source:['合计', '平均']},
							{data:'reportCurveConf'},
							{data:'code'},
							{data:'dataType'},
							{data:'remark'}
							];
				}else if(reportType==2){
					colHeaders=['','序号','字段','单位','数据来源','统计方式','显示级别','小数位数',loginUserLanguageResource.reportCurve,'','','',''];
					columns=[
							{data:'checked',type:'checkbox'},
							{data:'id'},
							{data:'title'},
						 	{data:'unit'},
						 	{data:'dataSource'},
						 	{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']},
							{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
							{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}},
							{data:'reportCurveConfShowValue'},
							{data:'reportCurveConf'},
							{data:'code'},
							{data:'dataType'},
							{data:'remark'}
							];
				}
				
				reportUnitContentConfigHandsontableHelper.colHeaders=colHeaders;
				reportUnitContentConfigHandsontableHelper.columns=columns;
				
				reportUnitContentConfigHandsontableHelper.hiddenColumns=[9,10,11,12];
		        reportUnitContentConfigHandsontableHelper.colWidths=[25,30,150,80,60,60,60,60,85,85];
		        
		        if(reportType==0){
		        	reportUnitContentConfigHandsontableHelper.hiddenColumns=[9,10,11,12];
			        reportUnitContentConfigHandsontableHelper.colWidths=[25,30,150,80,60,60,60,60,85,85];
		        }else if(reportType==1){
		        	reportUnitContentConfigHandsontableHelper.hiddenColumns=[12,13,14,15];
		        	reportUnitContentConfigHandsontableHelper.colWidths=[25,30,150,80,60,60,60,60,25,25,85,85];
		        }if(reportType==2){
		        	reportUnitContentConfigHandsontableHelper.hiddenColumns=[9,10,11,12];
			        reportUnitContentConfigHandsontableHelper.colWidths=[25,30,150,80,60,60,60,60,85,85];
		        }
				
				reportUnitContentConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportUnitContentConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ReportUnitContentConfigPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
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
	        reportUnitContentConfigHandsontableHelper.hiddenColumns=[];
	        reportUnitContentConfigHandsontableHelper.colWidths=[];
	        
	        reportUnitContentConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        reportUnitContentConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportUnitContentConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+reportUnitContentConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportUnitContentConfigHandsontableHelper.divid);
	        	reportUnitContentConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: reportUnitContentConfigHandsontableHelper.hiddenColumns,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: reportUnitContentConfigHandsontableHelper.colWidths,
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
	                    var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	                    
	                    var readOnlyStartCol=1;
	                    var readOnlyEndCol=4;
	                    
	                    var curveShowCol=8;
	                    if(reportType==1){
	                    	curveShowCol=10;
	                    }
	                    
	                    if(protocolConfigModuleEditFlag==1){
	                    	var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                	if(reportUnitTreeSelectedRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                		if(selectedItem.data.classes==0){
		                			cellProperties.readOnly = true;
		                		}else{
		                			if(reportUnitContentConfigHandsontableHelper!=null && reportUnitContentConfigHandsontableHelper.hot!=undefined){
		                				var rowData=reportUnitContentConfigHandsontableHelper.hot.getDataAtRow(visualRowIndex);
		                				if(rowData[0]){
		                					if (visualColIndex >=readOnlyStartCol && visualColIndex<=readOnlyEndCol) {
				    							cellProperties.readOnly = true;
				    		                }else{
				    		                	cellProperties.readOnly = false;
				    		                }
		                				}else{
		                					if (visualColIndex >=1) {
				    							cellProperties.readOnly = true;
				    		                }
		                				}
		                			}
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    
	                    if(visualColIndex==curveShowCol){
		                	cellProperties.renderer = reportUnitContentConfigHandsontableHelper.addCurveBg;
		                }else{
		                	if(reportUnitContentConfigHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& reportUnitContentConfigHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = reportUnitContentConfigHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(reportUnitContentConfigHandsontableHelper!=null && reportUnitContentConfigHandsontableHelper.hot!=undefined){
	                		var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	                		
	                		var curveShowValueCol=8;
	                		var curveConfigValueCol=9;
	                		var selectedTableType=21;
	                		if(reportType==0){
	                			curveShowValueCol=8;
		                		curveConfigValueCol=9;
		                		selectedTableType=21;
	                		}else if(reportType==1){
	                			curveShowValueCol=10;
		                		curveConfigValueCol=12;
		                		selectedTableType=22;
	                		}else if(reportType==2){
	                			curveShowValueCol=8;
		                		curveConfigValueCol=9;
		                		selectedTableType=23;
	                		}
	                		var row1=reportUnitContentConfigHandsontableHelper.hot.getDataAtRow(row);
		                	if(row1[0] && (column==curveShowValueCol)){
		                		var reportUnitTreeSelectedRow= Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue();
		                		if(reportUnitTreeSelectedRow!=''){
		                			var selectedItem=Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().getAt(reportUnitTreeSelectedRow);
		                			if(selectedItem.data.classes==1){
		                				var CurveConfigWindow=Ext.create("AP.view.acquisitionUnit.CurveConfigWindow");
		                				
		                				Ext.getCmp("curveConfigSelectedTableType_Id").setValue(selectedTableType);//单井报表内容表
		                				Ext.getCmp("curveConfigSelectedRow_Id").setValue(row);
		                				Ext.getCmp("curveConfigSelectedCol_Id").setValue(column);
		                				
		                				CurveConfigWindow.show();
		                				
		                				var curveConfig=null;
		                				if(column==curveShowValueCol && isNotVal(row1[curveConfigValueCol])){
		                					curveConfig=row1[curveConfigValueCol];
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
	                	if(reportUnitContentConfigHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& reportUnitContentConfigHandsontableHelper!=null
	                		&& reportUnitContentConfigHandsontableHelper.hot!=''
	                		&& reportUnitContentConfigHandsontableHelper.hot!=undefined 
	                		&& reportUnitContentConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportUnitContentConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	if(row==row2 && column==column2 && column==0){
	                		var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
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
	                },
	                afterChange: function (changes, source) {
	                	if (changes != null && source=="edit") {
	                		var row=Ext.getCmp("ReportUnitContentConfig_SelectedRow").getValue();
	                		var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	                		
	                		for (var i = 0; i < changes.length; i++) {
	                			var index = changes[i][0]; //行号码 0-行号 1-列 2-旧值 3-新值
	                			var col=changes[i][1];
	                			var oldValue=changes[i][2];
	                			var newValue=changes[i][3];
	                            var rowdata = reportUnitContentConfigHandsontableHelper.hot.getDataAtRow(index);
	                            
	                            
	                            
	                            if (oldValue != newValue) {
	                            	if(col=='checked'){
	                            		if(newValue){//选中
	                            			if(reportType==0){
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,2,rowdata[2]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,3,rowdata[3]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,4,rowdata[4]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,rowdata[5]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,rowdata[6]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,rowdata[7]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,rowdata[8]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,rowdata[9]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,rowdata[11]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,rowdata[10]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,rowdata[12]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
	                            			}if(reportType==1){
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,2,rowdata[2]);//名称
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,3,rowdata[3]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,4,rowdata[4]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,rowdata[5]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,rowdata[6]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,rowdata[7]);
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,9,rowdata[8]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,10,rowdata[9]);
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,rowdata[10]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,rowdata[12]);
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,13,rowdata[11]);
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,rowdata[14]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,rowdata[13]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,rowdata[15]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
	                            			}else if(reportType==2){
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,2,rowdata[2]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,3,rowdata[3]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,4,rowdata[4]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,rowdata[5]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,rowdata[6]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,rowdata[7]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,rowdata[8]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,rowdata[9]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,rowdata[11]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,rowdata[10]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,rowdata[12]);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
	                            			}
	                            			
	                            		}else{//反选清空
	                            			if(reportType==0){
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,2,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,3,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,4,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
                            				}else if(reportType==1){
                            					reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,2,'');//名称
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,3,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,4,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,'');
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,9,false);
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,10,false);
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,'');
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,13,'');
			                            		
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,'');
			                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
                            				}else if(reportType==2){
                            					reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,2,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,3,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,4,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,'');
    		                            		reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
                            				}
	                            			
	                            		}
	                            	}else{
	                            		if(rowdata[0]){
	                            			if(col=='totalType'){
	                            				var colIndex=5;
	                            				if(reportType==0){
	                            					colIndex=5;
	                            				}else if(reportType==1){
	                            					colIndex=5;
	                            				}else if(reportType==2){
	                            					colIndex=5;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,5,rowdata[colIndex]);
	    	                            	}else if(col=='showLevel'){
	                            				var colIndex=6;
	                            				if(reportType==0){
	                            					colIndex=6;
	                            				}else if(reportType==1){
	                            					colIndex=6;
	                            				}else if(reportType==2){
	                            					colIndex=6;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,6,rowdata[colIndex]);
	    	                            	}else if(col=='prec'){
	                            				var colIndex=7;
	                            				if(reportType==0){
	                            					colIndex=7;
	                            				}else if(reportType==1){
	                            					colIndex=7;
	                            				}else if(reportType==2){
	                            					colIndex=7;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,8,rowdata[colIndex]);
	    	                            	}else if(col=='sumSign'){
	    	                            		if(reportType==1){
	    	                            			var colIndex=8;
	    	                            			reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,9,rowdata[colIndex]);
	    	                            		}
	    	                            	}else if(col=='averageSign'){
	    	                            		if(reportType==1){
	    	                            			var colIndex=9;
	    	                            			reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,10,rowdata[colIndex]);
	    	                            		}
	    	                            	}else if(col=='reportCurveConfShowValue'){
	                            				var colIndex=8;
	                            				if(reportType==0){
	                            					colIndex=8;
	                            				}else if(reportType==2){
	                            					colIndex=10;
	                            				}else if(reportType==2){
	                            					colIndex=8;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,11,rowdata[colIndex]);
	    	                            	}else if(col=='curveStatType'){
	    	                            		if(reportType==1){
	    	                            			var colIndex=11;
	    	                            			reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,13,rowdata[colIndex]);
	    	                            		}
	    	                            	}else if(col=='reportCurveConf'){
	                            				var colIndex=9;
	                            				if(reportType==0){
	                            					colIndex=9;
	                            				}else if(reportType==1){
	                            					colIndex=12;
	                            				}else if(reportType==2){
	                            					colIndex=9;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,12,rowdata[colIndex]);
	    	                            	}else if(col=='dataType'){
	                            				var colIndex=11;
	                            				if(reportType==0){
	                            					colIndex=11;
	                            				}else if(reportType==1){
	                            					colIndex=14;
	                            				}else if(reportType==2){
	                            					colIndex=11;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,14,rowdata[colIndex]);
	    	                            	}else if(col=='code'){
	                            				var colIndex=10;
	                            				if(reportType==0){
	                            					colIndex=10;
	                            				}else if(reportType==1){
	                            					colIndex=13;
	                            				}else if(reportType==2){
	                            					colIndex=10;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,15,rowdata[colIndex]);
	    	                            	}else if(col=='remark'){
	                            				var colIndex=12;
	                            				if(reportType==0){
	                            					colIndex=12;
	                            				}else if(reportType==1){
	                            					colIndex=15;
	                            				}else if(reportType==2){
	                            					colIndex=12;
	                            				}
	                            				reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,16,rowdata[colIndex]);
	    	                            	}
	                            			
	                            			reportUnitContentConfigColInfoHandsontableHelper.hot.setDataAtCell(row,17,1);
	                            		}
	                            	}
	                            }
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

function CreateReportUnitContentConfigColInfoTable(){
	if(reportUnitContentConfigColInfoHandsontableHelper!=null){
		if(reportUnitContentConfigColInfoHandsontableHelper.hot!=undefined){
			reportUnitContentConfigColInfoHandsontableHelper.hot.destroy();
		}
		reportUnitContentConfigColInfoHandsontableHelper=null;
	}
	
	var calculateType=Ext.getCmp("ReportUnitContentConfig_CalculateType").getValue();
	var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	var unitId=Ext.getCmp("ReportUnitContentConfig_UnitId").getValue();
	var unitName=Ext.getCmp("ReportUnitContentConfig_UnitName").getValue();
	var classes=Ext.getCmp("ReportUnitContentConfig_Classes").getValue();
	var templateCode=Ext.getCmp("ReportUnitContentConfig_TemplateCode").getValue();
	
	var row=Ext.getCmp("ReportUnitContentConfig_SelectedRow").getValue();
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(reportUnitContentConfigColInfoHandsontableHelper==null || reportUnitContentConfigColInfoHandsontableHelper.hot==undefined){
				reportUnitContentConfigColInfoHandsontableHelper = ReportUnitContentConfigColInfoHandsontableHelper.createNew("ReportUnitContentConfigColInfoDiv_Id");
				var colHeaders=['序号','表头','字段','单位','数据来源','统计方式','显示级别','顺序','小数位数',
					'求和','求平均',loginUserLanguageResource.reportCurve,'曲线配置','曲线统计类型','数据类型','字段代码','备注','数据是否改变'];
				var columns=[
						{data:'id'},
						{data:'headerName'},
						{data:'itemName'},
					 	{data:'unit'},
					 	{data:'dataSource'},
					 	{data:'totalType'},
						{data:'showLevel'},
						{data:'sort'},
						{data:'prec'},
						{data:'sumSign'},
						{data:'averageSign'},
						{data:'reportCurveConfShowValue'},
						{data:'reportCurveConf'},
						{data:'curveStatType'},
						{data:'dataType'},
						{data:'itemCode'},
						{data:'remark'},
						{data:'dataChange'}
						];
				
				reportUnitContentConfigColInfoHandsontableHelper.colHeaders=colHeaders;
				reportUnitContentConfigColInfoHandsontableHelper.columns=columns;
				reportUnitContentConfigColInfoHandsontableHelper.rawData=result.rawData;
				reportUnitContentConfigColInfoHandsontableHelper.createTable(result.totalRoot);
			}else{
				reportUnitContentConfigColInfoHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			calculateType: calculateType,
			reportType: reportType,
			unitId: unitId,
			templateCode: templateCode,
			classes: classes,
			row: row
        }
	});
};

var ReportUnitContentConfigColInfoHandsontableHelper = {
		createNew: function (divid) {
	        var reportUnitContentConfigColInfoHandsontableHelper = {};
	        reportUnitContentConfigColInfoHandsontableHelper.hot1 = '';
	        reportUnitContentConfigColInfoHandsontableHelper.divid = divid;
	        reportUnitContentConfigColInfoHandsontableHelper.validresult=true;//数据校验
	        reportUnitContentConfigColInfoHandsontableHelper.colHeaders=[];
	        reportUnitContentConfigColInfoHandsontableHelper.columns=[];
	        reportUnitContentConfigColInfoHandsontableHelper.AllData=[];
	        reportUnitContentConfigColInfoHandsontableHelper.rawData=[];
	        
	        reportUnitContentConfigColInfoHandsontableHelper.addWhiteBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(255, 255, 255)';    
	             td.style.whiteSpace='nowrap'; //文本不换行
	             td.style.overflow='hidden';//超出部分隐藏
	             td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportUnitContentConfigColInfoHandsontableHelper.addDataChangeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(255, 76, 66)'; 
	             td.style.whiteSpace='nowrap'; //文本不换行
	             td.style.overflow='hidden';//超出部分隐藏
	             td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportUnitContentConfigColInfoHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        reportUnitContentConfigColInfoHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        reportUnitContentConfigColInfoHandsontableHelper.rowDataChange = function(row){
	        	var rowdata = reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtRow(row);
	        	var rawData=reportUnitContentConfigColInfoHandsontableHelper.rawData[parseInt(row)];
	        	var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	        	var dataChange=false;
	        	if(reportType==0){
	        		if( ( !isEquals(rowdata[2],rawData.itemName) )
	        				|| !isEquals(rowdata[5],rawData.totalType) 
	        				|| !isEquals(rowdata[6],rawData.showLevel )
	        				|| !isEquals(rowdata[8],rawData.prec )
	        				|| !isEquals(rowdata[11],rawData.reportCurveConfShowValue )
	        				|| !isEquals(rowdata[12],rawData.reportCurveConf )
	        				|| !isEquals(rowdata[14],rawData.dataType )
	        				|| !isEquals(rowdata[15],rawData.itemCode )
	        				){
	        			dataChange=true;
	        		}
	        	}else if(reportType==1){
	        		if( ( !isEquals(rowdata[2],rawData.itemName) )
	        				|| !isEquals(rowdata[5],rawData.totalType) 
	        				|| !isEquals(rowdata[6],rawData.showLevel )
	        				|| !isEquals(rowdata[8],rawData.prec )
	        				|| rowdata[9]!=rawData.sumSign
	        				|| rowdata[10]!=rawData.averageSign
	        				|| !isEquals(rowdata[11],rawData.reportCurveConfShowValue )
	        				|| !isEquals(rowdata[12],rawData.reportCurveConf )
	        				|| !isEquals(rowdata[13],rawData.curveStatType )
	        				|| !isEquals(rowdata[14],rawData.dataType )
	        				|| !isEquals(rowdata[15],rawData.itemCode )
	        				){
	        			dataChange=true;
	        		}
	        	}else if(reportType==2){
	        		if( ( !isEquals(rowdata[2],rawData.itemName) )
	        				|| !isEquals(rowdata[5],rawData.totalType) 
	        				|| !isEquals(rowdata[6],rawData.showLevel )
	        				|| !isEquals(rowdata[8],rawData.prec )
	        				|| !isEquals(rowdata[11],rawData.reportCurveConfShowValue )
	        				|| !isEquals(rowdata[12],rawData.reportCurveConf )
	        				|| !isEquals(rowdata[14],rawData.dataType )
	        				|| !isEquals(rowdata[15],rawData.itemCode )
	        				){
	        			dataChange=true;
	        		}
	        	}
	        	
	        	return dataChange;
	        }
	        
	        reportUnitContentConfigColInfoHandsontableHelper.createTable = function (data) {
	        	$('#'+reportUnitContentConfigColInfoHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+reportUnitContentConfigColInfoHandsontableHelper.divid);
	        	reportUnitContentConfigColInfoHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [3,4,5,6,7,8,9,10,11,12,13,14,15,16,17],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [50,150,150,80,60,60,60,60,85,80],
	                columns:reportUnitContentConfigColInfoHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:reportUnitContentConfigColInfoHandsontableHelper.colHeaders,//显示列头
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
		                	cellProperties.renderer = reportUnitContentConfigColInfoHandsontableHelper.addCurveBg;
		                }else if(visualColIndex==2){
	                    	if(reportUnitContentConfigColInfoHandsontableHelper.hot!=undefined && reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtCell!=undefined){
//	                    		var dataChange=reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtCell(visualRowIndex,17);
	                    		
	                    		var dataChange=reportUnitContentConfigColInfoHandsontableHelper.rowDataChange(visualRowIndex);
	                    		
	                    		var rowdata = reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtRow(visualRowIndex);
	                    		
	                    		if(dataChange){
	                    			cellProperties.renderer=reportUnitContentConfigColInfoHandsontableHelper.addDataChangeBg;
	                    		}else{
	                    			cellProperties.renderer = reportUnitContentConfigColInfoHandsontableHelper.addWhiteBg;
	                    		}
	                    	}
	                    }else if(visualColIndex<=2){
	                    	cellProperties.renderer = reportUnitContentConfigColInfoHandsontableHelper.addCellStyle;
	                    }
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	if(reportUnitContentConfigColInfoHandsontableHelper!=null && reportUnitContentConfigColInfoHandsontableHelper.hot!=undefined){
		                	if(column==9){
		                		alert("配置此列内容!");
		                	}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(reportUnitContentConfigColInfoHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& reportUnitContentConfigColInfoHandsontableHelper!=null
	                		&& reportUnitContentConfigColInfoHandsontableHelper.hot!=''
	                		&& reportUnitContentConfigColInfoHandsontableHelper.hot!=undefined 
	                		&& reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	if(row>=0 || row2>=0){
	                		var startRow=row;
	                    	var endRow=row2;
	                    	if(row>row2){
	                    		startRow=row2;
	                        	endRow=row;
	                    	}
	                    	var reportType=Ext.getCmp("ReportUnitContentConfig_ReportType").getValue();
	                    	var rowdata = reportUnitContentConfigColInfoHandsontableHelper.hot.getDataAtRow(startRow);
	                    	var code=rowdata[15];
	                    	var totalType=rowdata[5];
	                    	Ext.getCmp("ReportUnitContentConfig_SelectedRow").setValue(startRow);
	                    	CreateReportUnitContentConfigTable();
	                    	
//	                    	var reportUnitContentConfigData=reportUnitContentConfigHandsontableHelper.hot.getData();
	                    	
//	                    	for(var i=0;i<reportUnitContentConfigData.length;i++){
//	                    		var contentCode=reportUnitContentConfigData[i][10];
//	                    		if(reportType==0){
//	                    			contentCode=reportUnitContentConfigData[i][10];
//	                    		}
//	                    		
//	                    		if(contentCode.toUpperCase()==code.toUpperCase()){
//	                    			if(reportType==0){
//	                    				if(isNotVal(reportUnitContentConfigData[i][5])){//统计类型
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,5,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][6])){//显示级别
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,6,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][7])){//小数位数
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,7,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][8])){//曲线显示值
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,8,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][9])){//曲线配置
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,9,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][10])){//字段代码
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,10,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][11])){//数据类型
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,11,'');
//	                    				}
//	                    				if(isNotVal(reportUnitContentConfigData[i][12])){//备注
//	                    					reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,12,'');
//	                    				}
//	                    			}
//	                    			
//	                    			if(!reportUnitContentConfigData[i][0]){
//		                    			reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,0,true);
//		                    		}
//	                    			if(reportType==0){
//	                    				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,5,rowdata[5]);//统计类型
//	                    				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,6,rowdata[6]);//显示级别
//	                    				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,7,rowdata[8]);//小数位数
//	                    				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(i,8,rowdata[11]);//曲线显示值
//	                    			}
//	                    			
//	                    			break;
//	                    		}
//	                		}
	                    	
//	                    	var columns=[
//	    						{data:'id'},
//	    						{data:'headerName'},
//	    						{data:'itemName'},
//	    					 	{data:'unit'},
//	    					 	{data:'dataSource'},
//	    					 	{data:'totalType'},
//	    						{data:'showLevel'},
//	    						{data:'sort'},
//	    						{data:'prec'},
//	    						{data:'sumSign'},
//	    						{data:'averageSign'},
//	    						{data:'reportCurveConfShowValue'},
//	    						{data:'reportCurveConf'},
//	    						{data:'curveStatType'},
//	    						{data:'dataType'},
//	    						{data:'itemCode'},
//	    						{data:'remark'}
//	    						];
//	                    	
//	                    	var columns="[" 
//	    						+"{data:'checked',type:'checkbox'}," 
//	    						+"{data:'id'}," 
//	    						+"{data:'title'},"
//	    					 	+"{data:'unit'},"
//	    					 	+"{data:'dataSource'}," 
//	    					 	+"{data:'totalType',type:'dropdown',strict:true,allowInvalid:false,source:['最大值', '最小值','平均值','最新值','最旧值','日累计']}," 
//	    						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}}," 
//	    						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,reportUnitContentConfigHandsontableHelper);}}," 
//	    						+"{data:'reportCurveConfShowValue'},"
//	    						+"{data:'reportCurveConf'},"
//	    						+"{data:'code'},"
//	    						+"{data:'dataType'},"
//	    						+"{data:'remark'}"
//	    						+"]";
	                	}
	                }
	        	});
	        }
	        //保存数据
	        reportUnitContentConfigColInfoHandsontableHelper.saveData = function () {}
	        reportUnitContentConfigColInfoHandsontableHelper.clearContainer = function () {
	        	reportUnitContentConfigColInfoHandsontableHelper.AllData = [];
	        }
	        return reportUnitContentConfigColInfoHandsontableHelper;
	    }
};

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
    	if (reportUnitContentConfigColInfoHandsontableHelper.rowDataChange(index)) {
        	var item={};
        	item.matrix='0,0,0';
        	if(reportType==0){
        		item.itemName = itemsConfigData[index][2];
        		item.totalType=0;
        		item.dataSource=itemsConfigData[index][4];
        		item.itemShowLevel = itemsConfigData[index][6];
        		item.itemSort = index+1;
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(itemsConfigData[index][11]) && isNotVal(itemsConfigData[index][12])){
    				reportCurveConfig=itemsConfigData[index][12];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = itemsConfigData[index][15];
        		item.dataType = itemsConfigData[index][14];
        		
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
        		item.itemSort = index+1;
        		
        		var reportCurveConfig=null;
        		var reportCurveConfigStr="";
    			if(isNotVal(itemsConfigData[index][11]) && isNotVal(itemsConfigData[index][12])){
    				reportCurveConfig=itemsConfigData[index][12];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		
        		item.reportCurveConf=reportCurveConfigStr;
            	
        		item.itemCode = itemsConfigData[index][15];
        		item.dataType = itemsConfigData[index][14];
        		
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
        		item.itemSort = index+1;
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
    			if(isNotVal(itemsConfigData[index][11]) && isNotVal(itemsConfigData[index][12])){
    				reportCurveConfig=itemsConfigData[index][12];
    				reportCurveConfigStr=JSON.stringify(reportCurveConfig);
    			}
        		
        		item.reportCurveConf=reportCurveConfigStr;
        		
        		item.curveStatType = itemsConfigData[index][13];
        		if(item.curveStatType=='合计'){
        			item.curveStatType='1';
        		}else if(item.curveStatType=='平均'){
        			item.curveStatType='2';
        		}else if(item.curveStatType=='最大值'){
        			item.curveStatType='3';
        		}else if(item.curveStatType=='最小值'){
        			item.curveStatType='4';
        		}
            	
        		item.dataType = itemsConfigData[index][14]+"";
        		item.itemCode = itemsConfigData[index][15];
        		
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
    if(saveData.itemList.length>0){
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
                	CreateReportUnitContentConfigColInfoTable();
                	CreateReportUnitContentConfigTable();
                	if(reportType==0){
                		CreateSingleWellRangeReportTotalItemsInfoTable();
                	}else if(reportType==1){
                		CreateproductionReportTotalItemsInfoTable();
                	}else if(reportType==2){
                		CreateSingleWellDailyReportTotalItemsInfoTable();
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
    }else{
    	Ext.Msg.alert(cosog.string.ts, "<font color=blue>无数据变化</font>");
    }
    
    return false;
}