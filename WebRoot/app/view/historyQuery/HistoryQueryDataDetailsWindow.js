var deviceHistoryQueryDataHandsontableHelper=null;
Ext.define("AP.view.historyQuery.HistoryQueryDataDetailsWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.historyQueryDataDetailsWindow',
    layout: 'fit',
    title:'详细数据',
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
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[{
                id: 'HistoryQueryDataDetailsWindowRecord_Id',
                xtype: 'textfield',
                value: -1,
                hidden: true
            },{
                id: 'HistoryQueryDataDetailsWindowDeviceName_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'HistoryQueryDataDetailsWindowDeviceId_Id',
                xtype: 'textfield',
                value: '0',
                hidden: true
            },{
                id: 'HistoryQueryDataDetailsWindowDeviceCalculateType_Id',
                xtype: 'textfield',
                value: '0',
                hidden: true
            }],
            id:'HistoryQueryDataDetailsPanel_Id',
        	html: '<div id="HistoryQueryDataDetailsDiv_Id" style="width:100%;height:100%;"></div>',
            listeners: {
                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                	if(deviceHistoryQueryDataHandsontableHelper!=null&&deviceHistoryQueryDataHandsontableHelper.hot!=null&&deviceHistoryQueryDataHandsontableHelper.hot!=undefined){
                		var newWidth=width;
                		var newHeight=height;
                		var header=thisPanel.getHeader();
                		if(header){
                			newHeight=newHeight-header.lastBox.height-2;
                		}
                		deviceHistoryQueryDataHandsontableHelper.hot.updateSettings({
                			width:newWidth,
                			height:newHeight
                		});
                	}else{
                		var recordId=Ext.getCmp("HistoryQueryDataDetailsWindowRecord_Id").getValue();
                		var deviceId=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceId_Id").getValue()
                		var deviceName=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceName_Id").getValue();
                		var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                		var calculateType=Ext.getCmp("HistoryQueryDataDetailsWindowDeviceCalculateType_Id").getValue();
              			CreateDeviceHistoryQueryDataTable(recordId,deviceId,deviceName,deviceType,calculateType);
                	}
                },
                beforeclose: function ( panel, eOpts) {
                	if(deviceHistoryQueryDataHandsontableHelper!=null){
    					if(deviceHistoryQueryDataHandsontableHelper.hot!=undefined){
    						deviceHistoryQueryDataHandsontableHelper.hot.destroy();
    					}
    					deviceHistoryQueryDataHandsontableHelper=null;
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


function CreateDeviceHistoryQueryDataTable(recordId,deviceId,deviceName,deviceType,calculateType){
	Ext.getCmp("HistoryQueryDataDetailsPanel_Id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getDeviceHistoryDetailsData',
		success:function(response) {
			Ext.getCmp("HistoryQueryDataDetailsPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(deviceHistoryQueryDataHandsontableHelper==null || deviceHistoryQueryDataHandsontableHelper.hot==undefined){
				deviceHistoryQueryDataHandsontableHelper = DeviceHistoryQueryDataHandsontableHelper.createNew("HistoryQueryDataDetailsDiv_Id");
				var colHeaders="['名称','变量','名称','变量','名称','变量']";
				var columns="[" 
						+"{data:'name1'}," 
						+"{data:'value1'}," 
						+"{data:'name2'},"
						+"{data:'value2'}," 
						+"{data:'name3'}," 
						+"{data:'value3'}"
						+"]";
				deviceHistoryQueryDataHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceHistoryQueryDataHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceHistoryQueryDataHandsontableHelper.CellInfo=result.CellInfo;
				if(result.totalRoot.length==0){
					deviceHistoryQueryDataHandsontableHelper.sourceData=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					deviceHistoryQueryDataHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					deviceHistoryQueryDataHandsontableHelper.sourceData=result.totalRoot;
					deviceHistoryQueryDataHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				deviceHistoryQueryDataHandsontableHelper.CellInfo=result.CellInfo;
				deviceHistoryQueryDataHandsontableHelper.sourceData=result.totalRoot;
				deviceHistoryQueryDataHandsontableHelper.hot.loadData(result.totalRoot);
			}
			//添加单元格属性
			for(var i=0;i<deviceHistoryQueryDataHandsontableHelper.CellInfo.length;i++){
				var row=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
				var col=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col;
				var column=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].column;
				var columnDataType=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].columnDataType;
				deviceHistoryQueryDataHandsontableHelper.hot.setCellMeta(row,col,'columnDataType',columnDataType);
			}
		},
		failure:function(){
			Ext.getCmp("HistoryQueryDataDetailsPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			recordId: recordId,
			deviceType: deviceType,
			deviceId: deviceId,
			deviceName: deviceName,
			calculateType: calculateType
        }
	});
};

var DeviceHistoryQueryDataHandsontableHelper = {
		createNew: function (divid) {
	        var deviceHistoryQueryDataHandsontableHelper = {};
	        deviceHistoryQueryDataHandsontableHelper.divid = divid;
	        deviceHistoryQueryDataHandsontableHelper.validresult=true;//数据校验
	        deviceHistoryQueryDataHandsontableHelper.colHeaders=[];
	        deviceHistoryQueryDataHandsontableHelper.columns=[];
	        deviceHistoryQueryDataHandsontableHelper.CellInfo=[];
	        
	        deviceHistoryQueryDataHandsontableHelper.sourceData=[];
	        
	        deviceHistoryQueryDataHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = '#DC2828';   
	             td.style.color='#FFFFFF';
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
            	
//	            if(row>0){
//	            	td.style.textAlign='left';//内容左对齐
//	            }
	            
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
	            if (row ==0) {
	            	Handsontable.renderers.TextRenderer.apply(this, arguments);
//		        	td.style.fontWeight = 'bold';
			        td.style.fontSize = '20px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '40px';
	            }
	            if (row%2==1&&row>0) {
	            	td.style.backgroundColor = '#f5f5f5';
                }
	            if (col%2==0) {
//	            	td.style.fontWeight = 'bold';
                }else{
                	td.style.fontFamily = 'SimHei';
                }
	            for(var i=0;i<deviceHistoryQueryDataHandsontableHelper.CellInfo.length;i++){
	            	if( isNotVal(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyColor) ){
                		var row2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col*2;
        				if(row==row2 && col==col2 ){
        					td.style.color='#'+deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyColor;
        				}
                	}
                	
                	if( isNotVal(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyBgColor) ){
                		var row2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col*2;
        				if(row==row2 && col==col2 ){
        					td.style.backgroundColor = '#' + deviceHistoryQueryDataHandsontableHelper.CellInfo[i].historyBgColor;
        				}
                	}
	            	
	            	
	            	if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel>=0){
                		var row2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceHistoryQueryDataHandsontableHelper.CellInfo[i].col*2+1;
        				if(row==row2 && col==col2 ){
        					if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel>0){
        						td.style.fontWeight = 'bold';
       			             	td.style.fontFamily = 'SimHei';
        					}
   			             	if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==0){
			             		if(AlarmShowStyle.Data.Normal.Opacity!=0){
			             			td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.Normal.BackgroundColor,AlarmShowStyle.Data.Normal.Opacity);
			             		}
			             		td.style.color='#'+AlarmShowStyle.Data.Normal.Color;
			             	}else if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==100){
        						if(AlarmShowStyle.Data.FirstLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor,AlarmShowStyle.Data.FirstLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.FirstLevel.Color;
        					}else if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==200){
        						if(AlarmShowStyle.Data.SecondLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor,AlarmShowStyle.Data.SecondLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.SecondLevel.Color;
        					}else if(deviceHistoryQueryDataHandsontableHelper.CellInfo[i].alarmLevel==300){
        						if(AlarmShowStyle.Data.ThirdLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor,AlarmShowStyle.Data.ThirdLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.ThirdLevel.Color;
        					}
        				}
                	}
    			}
	        }
	        
	        deviceHistoryQueryDataHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceHistoryQueryDataHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceHistoryQueryDataHandsontableHelper.divid);
	        	deviceHistoryQueryDataHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [30,20,30,20,30,20],
	                columns:deviceHistoryQueryDataHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: false,
	                rowHeights: [40],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: [{
                        "row": 0,
                        "col": 0,
                        "rowspan": 1,
                        "colspan": 6
                    }],
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = deviceHistoryQueryDataHandsontableHelper.addCellStyle;
	                    
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(deviceHistoryQueryDataHandsontableHelper!=null&&deviceHistoryQueryDataHandsontableHelper.hot!=''&&deviceHistoryQueryDataHandsontableHelper.hot!=undefined && deviceHistoryQueryDataHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var record=deviceHistoryQueryDataHandsontableHelper.sourceData[coords.row];
	                		var rawValue='';
	                		if(coords.col==0){
	                			rawValue=record.name1;
	                		}else if(coords.col==1){
	                			rawValue=record.value1;
	                		}else if(coords.col==2){
	                			rawValue=record.name2;
	                		}else if(coords.col==3){
	                			rawValue=record.value2;
	                		}else if(coords.col==4){
	                			rawValue=record.name3;
	                		}else if(coords.col==5){
	                			rawValue=record.value3;
	                		}
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
	        return deviceHistoryQueryDataHandsontableHelper;
	    }
};