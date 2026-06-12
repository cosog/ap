var itemRealtimeDataHandsontableHelper=null;
Ext.define("AP.view.realTimeMonitoring.ItemRealtimeDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemRealtimeDataWindow',
    id:'ItemRealtimeDataWindow_Id',
    layout: 'fit',
    title: loginUserLanguageResource.dynamicData,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 500,
    minWidth: 500,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	tbar:[{
                id: 'RealtimeDataItemName_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'RealtimeDataItemCode_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'RealtimeDataItemType_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'RealtimeDataItemResolutionMode_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'RealtimeDataItemBitIndex_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                hidden:false,
                handler: function (v, o) {
                	exportItemRealtimeDataTable();
                }
           },'-',{
                id: 'ItemRealtimeDataCount_Id',
                xtype: 'component',
                tpl: loginUserLanguageResource.totalCount + ':{count}',
                hidden: false,
                style: 'margin-right:15px'
            }],
            items:[{
            	region: 'center',
            	id:'ItemRealtimeDataPanel_Id',
        		layout: 'fit',
        		autoScroll: true,
        		layout: 'fit',
            	html: '<div id="ItemRealtimeDataDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(itemRealtimeDataHandsontableHelper!=null && itemRealtimeDataHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		itemRealtimeDataHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		CreateItemRealtimeDataTable();
                    	}
                    },
                    minimize: function (win, opts) {
                        win.collapse();
                    }
                }
            }],
            listeners: {
    			beforeclose: function ( panel, eOpts) {
    				if(itemRealtimeDataHandsontableHelper!=null){
    					if(itemRealtimeDataHandsontableHelper.hot!=undefined){
    						itemRealtimeDataHandsontableHelper.hot.destroy();
    					}
    					itemRealtimeDataHandsontableHelper=null;
    				}
    			},
    			afterrender: function ( panel, eOpts) {}
    		}
        });
        me.callParent(arguments);
    }
});

function exportItemRealtimeDataTable(){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var itemName=Ext.getCmp("RealtimeDataItemName_Id").getValue();
	var itemCode=Ext.getCmp("RealtimeDataItemCode_Id").getValue();
	var itemType=Ext.getCmp("RealtimeDataItemType_Id").getValue();
	var itemResolutionMode=Ext.getCmp("RealtimeDataItemResolutionMode_Id").getValue();
	var itemBitIndex=Ext.getCmp("RealtimeDataItemBitIndex_Id").getValue();
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
		calculateType=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
	}
	
	var timestamp=new Date().getTime();
	var key='exportItemRealTimeData_'+deviceId+'_'+itemCode+'_'+timestamp;
	var maskPanelId='ItemRealtimeDataPanel_Id';
	var url = context + '/realTimeMonitoringController/exportItemRealTimeData';
    var param = "&deviceId=" + deviceId
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + '&calculateType='+calculateType
    + "&itemName=" + URLencode(URLencode(itemName))
    + '&itemCode='+itemCode
    + '&itemType='+itemType
    + '&itemResolutionMode='+itemResolutionMode
    + '&itemBitIndex='+itemBitIndex
    + '&key='+key;
    
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}

function CreateItemRealtimeDataTable(){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var itemName=Ext.getCmp("RealtimeDataItemName_Id").getValue();
	var itemCode=Ext.getCmp("RealtimeDataItemCode_Id").getValue();
	var itemType=Ext.getCmp("RealtimeDataItemType_Id").getValue();
	var itemResolutionMode=Ext.getCmp("RealtimeDataItemResolutionMode_Id").getValue();
	var itemBitIndex=Ext.getCmp("RealtimeDataItemBitIndex_Id").getValue();
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
		calculateType=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
	}
	
	
	if(itemRealtimeDataHandsontableHelper!=null){
		if(itemRealtimeDataHandsontableHelper.hot!=undefined){
			itemRealtimeDataHandsontableHelper.hot.destroy();
		}
		itemRealtimeDataHandsontableHelper=null;
	}
	
	if(Ext.getCmp("ItemRealtimeDataPanel_Id")!=undefined){
		Ext.getCmp("ItemRealtimeDataPanel_Id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getItemRealTimeData',
		success:function(response) {
			if(isNotVal(Ext.getCmp("ItemRealtimeDataPanel_Id"))){
				Ext.getCmp("ItemRealtimeDataPanel_Id").getEl().unmask();
            }
			
			var result =  Ext.JSON.decode(response.responseText);
			
			updateTotalRecords(result.totalCount,"ItemRealtimeDataCount_Id");
			
			if(itemRealtimeDataHandsontableHelper==null || itemRealtimeDataHandsontableHelper.hot==undefined){
				itemRealtimeDataHandsontableHelper = ItemRealtimeDataHandsontableHelper.createNew("ItemRealtimeDataDiv_Id");
				var colHeaders=[loginUserLanguageResource.acqTime,itemName];
				var columns=[{
					data:'acqTime'
				},{
					data:'data'
				}];
				itemRealtimeDataHandsontableHelper.colHeaders=colHeaders;
				itemRealtimeDataHandsontableHelper.columns=columns;
				itemRealtimeDataHandsontableHelper.createTable(result.totalRoot);
			}else{
				itemRealtimeDataHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ItemRealtimeDataPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			calculateType:calculateType,
			itemName:itemName,
			itemCode:itemCode,
			itemType:itemType,
			itemResolutionMode:itemResolutionMode,
			itemBitIndex:itemBitIndex
        }
	});
};

var ItemRealtimeDataHandsontableHelper = {
		createNew: function (divid) {
	        var itemRealtimeDataHandsontableHelper = {};
	        itemRealtimeDataHandsontableHelper.divid = divid;
	        itemRealtimeDataHandsontableHelper.validresult=true;//数据校验
	        itemRealtimeDataHandsontableHelper.colHeaders=[];
	        itemRealtimeDataHandsontableHelper.columns=[];
	        
	        itemRealtimeDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        itemRealtimeDataHandsontableHelper.createTable = function (data) {
	        	if($('#'+itemRealtimeDataHandsontableHelper.divid)!=undefined && $('#'+itemRealtimeDataHandsontableHelper.divid)[0]!=undefined){
	        		$('#'+itemRealtimeDataHandsontableHelper.divid).empty();
		        	var hotElement = document.querySelector('#'+itemRealtimeDataHandsontableHelper.divid);
		        	itemRealtimeDataHandsontableHelper.hot = new Handsontable(hotElement, {
		        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
		        		theme: 'ht-theme-classic',
		        		data: data,
//		        		colWidths: [5,4],
		                columns:itemRealtimeDataHandsontableHelper.columns,
		                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
		                rowHeaders: true,//显示行头
		                colHeaders: itemRealtimeDataHandsontableHelper.colHeaders,
		                fixedColumnsStart:1,
		                autoWrapRow: false, //自动换行
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
		                    cellProperties.renderer = itemRealtimeDataHandsontableHelper.addCellStyle;
		                    
		                    cellProperties.editor = false;
		                    return cellProperties;
		                },
		                afterOnCellMouseOver: function(event, coords, TD){
		                	if(coords.col>=0 && coords.row>=0 && itemRealtimeDataHandsontableHelper!=null&&itemRealtimeDataHandsontableHelper.hot!=''&&itemRealtimeDataHandsontableHelper.hot!=undefined && itemRealtimeDataHandsontableHelper.hot.getDataAtCell!=undefined){
		                		var rawValue=itemRealtimeDataHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        }
	        return itemRealtimeDataHandsontableHelper;
	    }
};

