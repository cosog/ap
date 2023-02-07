var deviceHistoryCurveSetHandsontableHelper=null;
Ext.define("AP.view.historyQuery.HistoryCurveSetWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.historyCurveSetWindow',
    id: 'HistoryCurveSetWindow_Id',
    layout: 'fit',
    title:'历史曲线设置',
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
            loyout:'border',
            tbar:[{
                xtype: 'label',
                margin: '0 0 0 0',
                html: '<font color=red>Y轴坐标在设置的最大最小值基础上再次自适应</font>'
            },'->',{
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
                	var deviceType=0;
                	var selectRowId="RPCHistoryQueryInfoDeviceListSelectRow_Id";
                	var gridPanelId="RPCHistoryQueryDeviceListGridPanel_Id";
                	var divId="rpcHistoryQueryCurveDiv_Id";
                	if(activeId=="PCPHistoryQueryInfoPanel_Id"){
                		deviceType=1;
                		selectRowId="PCPHistoryQueryInfoDeviceListSelectRow_Id";
                		gridPanelId="PCPHistoryQueryDeviceListGridPanel_Id";
                		divId="pcpHistoryQueryCurveDiv_Id";
                	}
                	
                	var deviceName='';
                	var deviceId=0;
                	var selectRow= Ext.getCmp(selectRowId).getValue();
                	if(selectRow>=0){
                		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
                		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
                	}
                	
                	var chart = $("#"+divId).highcharts();
                	var curveSetData = deviceHistoryCurveSetHandsontableHelper.hot.getData();
                	var graphicSetData={};
                	graphicSetData.History=[];
                	
                	Ext.Array.each(curveSetData, function (name, index, countriesItSelf) {
                		var maxValue=null,minValue=0;
                		if(isNotVal(curveSetData[index][1])){
                			maxValue=parseFloat(curveSetData[index][1]);
                		}
                		if(isNotVal(curveSetData[index][2])){
                			minValue=parseFloat(curveSetData[index][2]);
                		}
                		chart.yAxis[index].update({max: maxValue,min: minValue});
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
                				Ext.MessageBox.alert("信息", "保存成功");
                			}
                		},
                		failure:function(){
                			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
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
        		layout: 'fit',
        		padding:0,
//                autoScroll: true,
        		html: '<div id="HistoryCurveSetTableDiv_Id" style="width:100%;height:100%;margin:0 0 0 0;"></div>',
        		listeners: {
        			resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(deviceHistoryCurveSetHandsontableHelper!=null&&deviceHistoryCurveSetHandsontableHelper.hot!=null&&deviceHistoryCurveSetHandsontableHelper.hot!=undefined){
//                    		deviceHistoryCurveSetHandsontableHelper.hot.refreshDimensions();
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
        	}],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(deviceHistoryCurveSetHandsontableHelper!=null){
    					if(deviceHistoryCurveSetHandsontableHelper.hot!=undefined){
    						deviceHistoryCurveSetHandsontableHelper.hot.destroy();
    					}
    					deviceHistoryCurveSetHandsontableHelper=null;
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
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	
	var deviceType=0;
	var selectRowId="RPCHistoryQueryInfoDeviceListSelectRow_Id";
	var gridPanelId="RPCHistoryQueryDeviceListGridPanel_Id";
	var divId="rpcHistoryQueryCurveDiv_Id";
	
	if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		deviceType=1;
		selectRowId="PCPHistoryQueryInfoDeviceListSelectRow_Id";
		gridPanelId="PCPHistoryQueryDeviceListGridPanel_Id";
		divId="pcpHistoryQueryCurveDiv_Id";
	}
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(selectRow>=0){
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getHistoryQueryCurveSetData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
//			result={
//				    "success": true,
//				    "totalCount": 7,
//				    "totalRoot": [{
//				        "curveName": "井下压力计-压力",
//				        "itemCode": "c_jxyljyl",
//				        "itemType": "0",
//				        "yAxisMaxValue": "20",
//				        "yAxisMinValue": "0"
//				    }, {
//				        "curveName": "井口套压",
//				        "itemCode": "c_jkty",
//				        "itemType": "0",
//				        "yAxisMaxValue": "6",
//				        "yAxisMinValue": "0"
//				    }, {
//				        "curveName": "产气量瞬时",
//				        "itemCode": "c_cqlss",
//				        "itemType": "0",
//				        "yAxisMaxValue": "",
//				        "yAxisMinValue": ""
//				    }, {
//				        "curveName": "产水量瞬时",
//				        "itemCode": "c_cslss",
//				        "itemType": "0",
//				        "yAxisMaxValue": "",
//				        "yAxisMinValue": ""
//				    }, {
//				        "curveName": "变频器输出",
//				        "itemCode": "c_bpqscdl",
//				        "itemType": "0",
//				        "yAxisMaxValue": "200",
//				        "yAxisMinValue": "0"
//				    }, {
//				        "curveName": "修正后井底流压",
//				        "itemCode": "c_xzhjdly",
//				        "itemType": "0",
//				        "yAxisMaxValue": "20",
//				        "yAxisMinValue": "0"
//				    }, {
//				        "curveName": "冲次",
//				        "itemCode": "c_cc",
//				        "itemType": "0",
//				        "yAxisMaxValue": "5",
//				        "yAxisMinValue": "0"
//				    }]
//				};
			if(deviceHistoryCurveSetHandsontableHelper==null || deviceHistoryCurveSetHandsontableHelper.hot==undefined){
				deviceHistoryCurveSetHandsontableHelper = DeviceHistoryCurveSetHandsontableHelper.createNew("HistoryCurveSetTableDiv_Id");
				var colHeaders="['曲线','Y轴预设最大值','Y轴预设最小值','项编码','项类型']";
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
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
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
	        
	        
	        
	        deviceHistoryCurveSetHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        deviceHistoryCurveSetHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        deviceHistoryCurveSetHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
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
	                    	cellProperties.renderer = deviceHistoryCurveSetHandsontableHelper.addBoldBg;
	                    	cellProperties.readOnly = true;
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {}
	        	});
	        }
	        return deviceHistoryCurveSetHandsontableHelper;
	    }
};