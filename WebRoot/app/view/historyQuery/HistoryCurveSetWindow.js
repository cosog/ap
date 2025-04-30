var deviceHistoryCurveSetHandsontableHelper=null;
var deviceHistoryCurveDataFilterSetHandsontableHelper=null;
Ext.define("AP.view.historyQuery.HistoryCurveSetWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.historyCurveSetWindow',
    id: 'HistoryCurveSetWindow_Id',
    layout: 'fit',
    title: loginUserLanguageResource.historyDiagramSet,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1000,
    minWidth: 1000,
    height: 500,
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
                	var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
                	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                	var selectRowId="HistoryQueryInfoDeviceListSelectRow_Id";
                	var gridPanelId="HistoryQueryDeviceListGridPanel_Id";
                	var divId="historyQueryCurveDiv_Id";
                	
                	var deviceName='';
                	var deviceId=0;
                	var selectRow= Ext.getCmp(selectRowId).getValue();
                	if(selectRow>=0){
                		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
                		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
                	}
                	
                	var chart = $("#"+divId).highcharts();
                	var curveSetData = deviceHistoryCurveSetHandsontableHelper.hot.getData();
                	var curveDataFilterSetData=deviceHistoryCurveDataFilterSetHandsontableHelper.hot.getData();
                	
                	var graphicSetData={};
                	graphicSetData.History=[];
                	graphicSetData.HistoryDataFilter={};
                	graphicSetData.HistoryDataFilter.commData=deviceHistoryCurveDataFilterSetHandsontableHelper.hot.getDataAtRowProp(0,'value');
                	graphicSetData.HistoryDataFilter.exceptionData=deviceHistoryCurveDataFilterSetHandsontableHelper.hot.getDataAtRowProp(1,'value');
                	
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
//                					var serieName=chart.yAxis[i].series[j].name.replace('（','(').split('(')[0];
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
                		
                		graphicSetData.History.push(graphicInfo);
                	});
                	
                	
                	
                	Ext.Ajax.request({
                		method:'POST',
                		url:context + '/historyQueryController/setHistoryDataGraphicInfo',
                		success:function(response) {
                			var result =  Ext.JSON.decode(response.responseText);
                			if (result.success) {
                				Ext.getCmp("HistoryCurveSetWindow_Id").close();
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
                			graphicSetData:JSON.stringify(graphicSetData)
                        }
                	});
                }
            }],
        	items:[{
        		region: 'center',
        		title: loginUserLanguageResource.yAxisConfig,
        		layout: 'fit',
        		html: '<div id="HistoryCurveSetTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(deviceHistoryCurveSetHandsontableHelper!=null&&deviceHistoryCurveSetHandsontableHelper.hot!=null&&deviceHistoryCurveSetHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		deviceHistoryCurveSetHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                  			CreateDeviceHistoryCurveSetTable();
                    	}
                    }
        		}
        	},{
        		region: 'east',
        		title: loginUserLanguageResource.dataFilter,
        		width:'40%',
        		collapsible: true,
                split: true,
        		layout: 'fit',
        		html: '<div id="HistoryCurveDataFilterSetTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(deviceHistoryCurveDataFilterSetHandsontableHelper!=null&&deviceHistoryCurveDataFilterSetHandsontableHelper.hot!=null&&deviceHistoryCurveDataFilterSetHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		deviceHistoryCurveDataFilterSetHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
        		}
        	}],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(deviceHistoryCurveSetHandsontableHelper!=null){
    					if(deviceHistoryCurveSetHandsontableHelper.hot!=undefined){
    						deviceHistoryCurveSetHandsontableHelper.hot.destroy();
    					}
    					deviceHistoryCurveSetHandsontableHelper=null;
    				}
                	
                	if(deviceHistoryCurveDataFilterSetHandsontableHelper!=null){
    					if(deviceHistoryCurveDataFilterSetHandsontableHelper.hot!=undefined){
    						deviceHistoryCurveDataFilterSetHandsontableHelper.hot.destroy();
    					}
    					deviceHistoryCurveDataFilterSetHandsontableHelper=null;
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


function CreateDeviceHistoryCurveSetTable(){
	var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
	
	var deviceType=0;
	var selectRowId="HistoryQueryInfoDeviceListSelectRow_Id";
	var gridPanelId="HistoryQueryDeviceListGridPanel_Id";
	var divId="historyQueryCurveDiv_Id";
	
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(selectRow>=0){
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
	}
	
	if(deviceHistoryCurveSetHandsontableHelper!=null){
		if(deviceHistoryCurveSetHandsontableHelper.hot!=undefined){
			deviceHistoryCurveSetHandsontableHelper.hot.destroy();
		}
		deviceHistoryCurveSetHandsontableHelper=null;
	}
	
	if(deviceHistoryCurveDataFilterSetHandsontableHelper!=null){
		if(deviceHistoryCurveDataFilterSetHandsontableHelper.hot!=undefined){
			deviceHistoryCurveDataFilterSetHandsontableHelper.hot.destroy();
		}
		deviceHistoryCurveDataFilterSetHandsontableHelper=null;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getHistoryQueryCurveSetData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(deviceHistoryCurveSetHandsontableHelper==null || deviceHistoryCurveSetHandsontableHelper.hot==undefined){
				deviceHistoryCurveSetHandsontableHelper = DeviceHistoryCurveSetHandsontableHelper.createNew("HistoryCurveSetTableDiv_Id");
				var colHeaders="['"+loginUserLanguageResource.curve+"','"+loginUserLanguageResource.yAxisMaxSetValue+"','"+loginUserLanguageResource.yAxisMinSetValue+"','"+loginUserLanguageResource.itemCode+"','"+loginUserLanguageResource.itemType+"']";
				var columns="[" 
						+"{data:'curveName'}," 
						+"{data:'yAxisMaxValue'}," 
						+"{data:'yAxisMinValue'},"
						+"{data:'itemCode'},"
						+"{data:'itemType'}"
						+"]";
				deviceHistoryCurveSetHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceHistoryCurveSetHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceHistoryCurveSetHandsontableHelper.createTable(result.totalRoot);
			}else{
				deviceHistoryCurveSetHandsontableHelper.hot.loadData(result.totalRoot);
			}
			
			
			if(deviceHistoryCurveDataFilterSetHandsontableHelper==null || deviceHistoryCurveDataFilterSetHandsontableHelper.hot==undefined){
				deviceHistoryCurveDataFilterSetHandsontableHelper = DeviceHistoryCurveDataFilterSetHandsontableHelper.createNew("HistoryCurveDataFilterSetTableDiv_Id");
				var colHeaders="['"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.whetherDisplay+"']";
				var columns="[" 
						+"{data:'title'},"
						+"{data:'value',type:'checkbox'}"
						+"]";
				deviceHistoryCurveDataFilterSetHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceHistoryCurveDataFilterSetHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceHistoryCurveDataFilterSetHandsontableHelper.createTable(result.dataFilterTotalRoot);
			}else{
				deviceHistoryCurveDataFilterSetHandsontableHelper.hot.loadData(result.dataFilterTotalRoot);
			}
			
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			deviceType:deviceType
        }
	});
};

var DeviceHistoryCurveSetHandsontableHelper = {
		createNew: function (divid) {
	        var deviceHistoryCurveSetHandsontableHelper = {};
	        deviceHistoryCurveSetHandsontableHelper.divid = divid;
	        deviceHistoryCurveSetHandsontableHelper.validresult=true;//数据校验
	        deviceHistoryCurveSetHandsontableHelper.colHeaders=[];
	        deviceHistoryCurveSetHandsontableHelper.columns=[];
	        
	        deviceHistoryCurveSetHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(col==0){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        deviceHistoryCurveSetHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceHistoryCurveSetHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceHistoryCurveSetHandsontableHelper.divid);
	        	deviceHistoryCurveSetHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [3,4],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:deviceHistoryCurveSetHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true,//显示行头
	                colHeaders: deviceHistoryCurveSetHandsontableHelper.colHeaders,
	                colWidths: [2,1,1,1,1],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if(visualColIndex==0){
	                    	cellProperties.readOnly = true;
	                    }
	                    cellProperties.renderer=deviceHistoryCurveSetHandsontableHelper.addCellStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(deviceHistoryCurveSetHandsontableHelper!=null&&deviceHistoryCurveSetHandsontableHelper.hot!=''&&deviceHistoryCurveSetHandsontableHelper.hot!=undefined && deviceHistoryCurveSetHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=deviceHistoryCurveSetHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return deviceHistoryCurveSetHandsontableHelper;
	    }
};

var DeviceHistoryCurveDataFilterSetHandsontableHelper = {
		createNew: function (divid) {
	        var deviceHistoryCurveDataFilterSetHandsontableHelper = {};
	        deviceHistoryCurveDataFilterSetHandsontableHelper.divid = divid;
	        deviceHistoryCurveDataFilterSetHandsontableHelper.validresult=true;//数据校验
	        deviceHistoryCurveDataFilterSetHandsontableHelper.colHeaders=[];
	        deviceHistoryCurveDataFilterSetHandsontableHelper.columns=[];
	        
	        deviceHistoryCurveDataFilterSetHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(col==0){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        deviceHistoryCurveDataFilterSetHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceHistoryCurveDataFilterSetHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceHistoryCurveDataFilterSetHandsontableHelper.divid);
	        	deviceHistoryCurveDataFilterSetHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [3,4],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:deviceHistoryCurveDataFilterSetHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: true,//显示行头
	                colHeaders: deviceHistoryCurveDataFilterSetHandsontableHelper.colHeaders,
	                colWidths: [2,1],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if(visualColIndex==0){
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=deviceHistoryCurveDataFilterSetHandsontableHelper.addCellStyle;
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(deviceHistoryCurveDataFilterSetHandsontableHelper!=null&&deviceHistoryCurveDataFilterSetHandsontableHelper.hot!=''&&deviceHistoryCurveDataFilterSetHandsontableHelper.hot!=undefined && deviceHistoryCurveDataFilterSetHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=deviceHistoryCurveDataFilterSetHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return deviceHistoryCurveDataFilterSetHandsontableHelper;
	    }
};