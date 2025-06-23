var deviceReportCurveSetHandsontableHelper=null;
Ext.define("AP.view.reportOut.ReportCurveSetWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.reportCurveSetWindow',
    id: 'ReportCurveSetWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.reportDiagramSet,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 700,
    minWidth: 600,
    height: 400,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    padding:0,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout:'border',
            tbar:[{
                xtype: 'label',
                margin: '0 0 0 0',
                html: '<font color=red>'+loginUserLanguageResource.diagramSetTooltip+'</font>'
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
                	var reportType=0;
                	var selectRowId="SingleWellDailyReportDeviceListSelectRow_Id";
                	var gridPanelId="SingleWellDailyReportGridPanel_Id";
                	var divId="SingleWellRangeReportCurveDiv_Id";
                	
                	var SingleWellReportTabPanelActiveId=Ext.getCmp("SingleWellReportTabPanel_Id").getActiveTab().id;
        			if(SingleWellReportTabPanelActiveId=='SingleWellDailyReportTabPanel_id'){
        				reportType=2;
        				divId="SingleWellDailyReportCurveDiv_Id";
        			}else if(SingleWellReportTabPanelActiveId=='SingleWellRangeReportTabPanel_id'){
        				reportType=0;
        				divId="SingleWellRangeReportCurveDiv_Id";
        			}
                	
                	
                	var deviceName='';
                	var deviceId=0;
                	var selectRow= Ext.getCmp(selectRowId).getValue();
                	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
                		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
                		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
                	}
                	
                	var chart = $("#"+divId).highcharts();
                	var curveSetData = deviceReportCurveSetHandsontableHelper.hot.getData();
                	var graphicSetData={};
                	
                	if(reportType==0){
                		graphicSetData.Report=[];
                    	
                    	Ext.Array.each(curveSetData, function (name, index, countriesItSelf) {
                    		var maxValue=null,minValue=null;
                    		if(isNotVal(curveSetData[index][1])){
                    			maxValue=parseFloat(curveSetData[index][1]);
                    		}
                    		if(isNotVal(curveSetData[index][2])){
                    			minValue=parseFloat(curveSetData[index][2]);
                    		}
                    		for(var i=0;i<chart.yAxis.length;i++){
                    			var match=false;
                    			if(chart.yAxis[i].series.length>0){
                    				for(var j=0;j<chart.yAxis[i].series.length;j++){
                    					var serieName=chart.yAxis[i].series[j].name;
                    					if(serieName==curveSetData[index][0] || serieName.replace('（','(').split('(')[0]==curveSetData[index][0].replace('（','(').split('(')[0]){
                    						match=true;
                    						chart.yAxis[i].update({max: maxValue,min: minValue});
                    						break;
                    					}
                    				}
                    			}
                    			
                    			if(match){
                    				break;
                    			}
                    		}
                    		
                    		var graphicInfo={};
                    		graphicInfo.yAxisMaxValue=isNotVal(curveSetData[index][1])?curveSetData[index][1]:"";
                    		graphicInfo.yAxisMinValue=isNotVal(curveSetData[index][2])?curveSetData[index][2]:"";
                    		graphicInfo.itemCode=curveSetData[index][3];
                    		graphicInfo.itemType=curveSetData[index][4];
                    		
                    		graphicSetData.Report.push(graphicInfo);
                    	});
                	}else if(reportType==2){
                		graphicSetData.DailyReport=[];
                    	
                    	Ext.Array.each(curveSetData, function (name, index, countriesItSelf) {
                    		var maxValue=null,minValue=null;
                    		if(isNotVal(curveSetData[index][1])){
                    			maxValue=parseFloat(curveSetData[index][1]);
                    		}
                    		if(isNotVal(curveSetData[index][2])){
                    			minValue=parseFloat(curveSetData[index][2]);
                    		}
                    		for(var i=0;i<chart.yAxis.length;i++){
                    			var match=false;
                    			if(chart.yAxis[i].series.length>0){
                    				for(var j=0;j<chart.yAxis[i].series.length;j++){
                    					var serieName=chart.yAxis[i].series[j].name;
                    					if(serieName==curveSetData[index][0] || serieName.replace('（','(').split('(')[0]==curveSetData[index][0].replace('（','(').split('(')[0]){
                    						match=true;
                    						chart.yAxis[i].update({max: maxValue,min: minValue});
                    						break;
                    					}
                    				}
                    			}
                    			
                    			if(match){
                    				break;
                    			}
                    		}
                    		
                    		var graphicInfo={};
                    		graphicInfo.yAxisMaxValue=isNotVal(curveSetData[index][1])?curveSetData[index][1]:"";
                    		graphicInfo.yAxisMinValue=isNotVal(curveSetData[index][2])?curveSetData[index][2]:"";
                    		graphicInfo.itemCode=curveSetData[index][3];
                    		graphicInfo.itemType=curveSetData[index][4];
                    		
                    		graphicSetData.DailyReport.push(graphicInfo);
                    	});
                	}
                	Ext.Ajax.request({
                		method:'POST',
                		url:context + '/reportDataMamagerController/setReportDataGraphicInfo',
                		success:function(response) {
                			var result =  Ext.JSON.decode(response.responseText);
                			if (result.success) {
                				Ext.getCmp("ReportCurveSetWindow_Id").close();
                				Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                			}
                		},
                		failure:function(){
                			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
                		},
                		params: {
                			deviceName:deviceName,
                			deviceId:deviceId,
                			deviceType:deviceType,
                			reportType:reportType,
                			graphicSetData:JSON.stringify(graphicSetData)
                        }
                	});
                }
            }],
        	items:[{
        		region: 'center',
        		layout: 'fit',
        		html: '<div id="ReportCurveSetTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(deviceReportCurveSetHandsontableHelper!=null&&deviceReportCurveSetHandsontableHelper.hot!=null&&deviceReportCurveSetHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		deviceReportCurveSetHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                  			CreateDeviceReportCurveSetTable();
                    	}
                    }
        		}
        	}],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(deviceReportCurveSetHandsontableHelper!=null){
    					if(deviceReportCurveSetHandsontableHelper.hot!=undefined){
    						deviceReportCurveSetHandsontableHelper.hot.destroy();
    					}
    					deviceReportCurveSetHandsontableHelper=null;
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


function CreateDeviceReportCurveSetTable(){
	var deviceType=getDeviceTypeFromTabId("ProductionReportRootTabPanel");
	var reportType=0;
	var selectRowId="SingleWellDailyReportDeviceListSelectRow_Id";
	var gridPanelId="SingleWellDailyReportGridPanel_Id";
	var divId="SingleWellRangeReportCurveDiv_Id";
	
	var secondActiveId = Ext.getCmp("DailyReportTabPanel").getActiveTab().id;
	if(secondActiveId=="SingleWellDailyReportTabPanel_Id"){
		selectRowId="SingleWellDailyReportDeviceListSelectRow_Id";
		gridPanelId="SingleWellDailyReportGridPanel_Id";
		
		var SingleWellReportTabPanelActiveId=Ext.getCmp("SingleWellReportTabPanel_Id").getActiveTab().id;
		if(SingleWellReportTabPanelActiveId=='SingleWellDailyReportTabPanel_id'){
			reportType=2;
			divId="SingleWellDailyReportCurveDiv_Id";
		}else if(SingleWellReportTabPanelActiveId=='SingleWellRangeReportTabPanel_id'){
			reportType=0;
			divId="SingleWellRangeReportCurveDiv_Id";
		}
	}else if(secondActiveId=="ProductionDailyReportTabPanel_Id"){
		reportType=1;
		selectRowId="ProductionDailyReportInstanceListSelectRow_Id";
		gridPanelId="ProductionDailyReportGridPanel_Id";
		divId="ProductionDailyReportCurveDiv_Id";
	}

	
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/reportDataMamagerController/getReportQueryCurveSetData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(deviceReportCurveSetHandsontableHelper==null || deviceReportCurveSetHandsontableHelper.hot==undefined){
				deviceReportCurveSetHandsontableHelper = DeviceReportCurveSetHandsontableHelper.createNew("ReportCurveSetTableDiv_Id");
				var colHeaders="['"+loginUserLanguageResource.curve+"','"+loginUserLanguageResource.yAxisMaxSetValue+"','"+loginUserLanguageResource.yAxisMinSetValue+"','"+loginUserLanguageResource.itemCode+"','"+loginUserLanguageResource.itemType+"']";
				var columns="[" 
						+"{data:'curveName'}," 
						+"{data:'yAxisMaxValue'}," 
						+"{data:'yAxisMinValue'},"
						+"{data:'itemCode'},"
						+"{data:'itemType'}"
						+"]";
				deviceReportCurveSetHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceReportCurveSetHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceReportCurveSetHandsontableHelper.createTable(result.totalRoot);
			}else{
				deviceReportCurveSetHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			reportType:reportType,
			deviceType:deviceType
        }
	});
};

var DeviceReportCurveSetHandsontableHelper = {
		createNew: function (divid) {
	        var deviceReportCurveSetHandsontableHelper = {};
	        deviceReportCurveSetHandsontableHelper.divid = divid;
	        deviceReportCurveSetHandsontableHelper.validresult=true;//数据校验
	        deviceReportCurveSetHandsontableHelper.colHeaders=[];
	        deviceReportCurveSetHandsontableHelper.columns=[];
	        
	        deviceReportCurveSetHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(col==0){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        deviceReportCurveSetHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceReportCurveSetHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceReportCurveSetHandsontableHelper.divid);
	        	deviceReportCurveSetHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [3,4],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:deviceReportCurveSetHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true,//显示行头
	                colHeaders: deviceReportCurveSetHandsontableHelper.colHeaders,
	                colWidths: [2,1,1,1,1],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                    items: {
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut
	                        }
	                    }
	                }, 
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if(visualColIndex==0){
	                    	cellProperties.readOnly = true;
	                    }
	                    cellProperties.renderer=deviceReportCurveSetHandsontableHelper.addCellStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && deviceReportCurveSetHandsontableHelper!=null&&deviceReportCurveSetHandsontableHelper.hot!=''&&deviceReportCurveSetHandsontableHelper.hot!=undefined && deviceReportCurveSetHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=deviceReportCurveSetHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return deviceReportCurveSetHandsontableHelper;
	    }
};