var protocolDisplayUnitAcqItemsConfigHandsontableHelper=null;
var protocolDisplayUnitCalItemsConfigHandsontableHelper=null;
var protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
var protocolDisplayUnitPropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolDisplayUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolDisplayUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolDisplayUnitConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加显示单元',
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addDisplayUnitInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolDisplayUnitConfigTreeData();
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
                    	title:'显示单元配置',
                    	layout: 'fit',
                    	id:"ModbusProtocolDisplayUnitConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolDisplayUnitPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolDisplayUnitPropertiesHandsontableHelper!=null && protocolDisplayUnitPropertiesHandsontableHelper.hot!=undefined){
//                            		protocolDisplayUnitPropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolDisplayUnitPropertiesHandsontableHelper.hot.updateSettings({
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
                    		title:'采集项配置',
                    		id:"ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ModbusProtocolDisplayUnitAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitAcqItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolDisplayUnitAcqItemsConfigHandsontableHelper!=null && protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot!=undefined){
//                                		protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	},{
                    		region: 'south',
                        	height:'50%',
                        	title:'计算项配置',
                        	collapsible: true,
                            split: true,
                        	layout: 'fit',
                        	id:"ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ModbusProtocolDisplayUnitCalItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitCalItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolDisplayUnitCalItemsConfigHandsontableHelper!=null && protocolDisplayUnitCalItemsConfigHandsontableHelper.hot!=undefined){
//                                		protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	}]
                	},{
                		region: 'east',
                		width:'40%',
                		title:'控制项配置',
                		id:"ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null && protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined){
//                            		protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	}] 
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateProtocolDisplayUnitAcqItemsConfigInfoTable(protocolName,classes,code,unitId,acqUnitId,unitName){
	Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayUnitAcqItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==2){
				Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(unitName+'/采集项配置');
			}else{
				Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle('采集项配置');
			}
			if(protocolDisplayUnitAcqItemsConfigHandsontableHelper==null || protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot==undefined){
				protocolDisplayUnitAcqItemsConfigHandsontableHelper = ProtocolDisplayUnitAcqItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','显示级别','显示顺序','实时曲线顺序','实时曲线颜色','历史曲线顺序','历史曲线颜色']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'isRealtimeCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeCurveColor'},"
						+"{data:'isHistoryCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}},"
						+"{data:'historyCurveColor'},"
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}," 
						+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}},"
						+"{data:'bitIndex'}"
						+"]";
				
				protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayUnitAcqItemsConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code,
			unitId:unitId,
			acqUnitId:acqUnitId
        }
	});
};

var ProtocolDisplayUnitAcqItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitAcqItemsConfigHandsontableHelper = {};
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot1 = '';
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.divid = divid;
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns=[];
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitAcqItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitAcqItemsConfigHandsontableHelper.divid);
	        	protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [10,11,12],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,50,140,80,60,60,85,85,85,85],
	                columns:protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                	if(ScadaDriverModbusConfigSelectRow!=''){
	                		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                		if(selectedItem.data.classes!=2){
	                			cellProperties.readOnly = true;
	                		}else{
	                			if (visualColIndex >=1 && visualColIndex<=3) {
	    							cellProperties.readOnly = true;
	    		                }else if(visualColIndex==7||visualColIndex==9){
	    		                	cellProperties.renderer = protocolDisplayUnitAcqItemsConfigHandsontableHelper.addCurveBg;
	    		                }
	                		}
	                	}
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	var row1=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtRow(row);
	                	if(row1[0] && (column==7||column==9)){
	                		var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                		if(ScadaDriverModbusConfigSelectRow!=''){
	                			var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                			if(selectedItem.data.classes==2){
	                				var CurveColorSelectWindow=Ext.create("AP.view.acquisitionUnit.CurveColorSelectWindow");
	                				Ext.getCmp("curveColorSelectedTableType_Id").setValue(0);//采集项表
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitAcqItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitAcqItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitAcqItemsConfigHandsontableHelper;
	    }
};

function CreateProtocolDisplayUnitCalItemsConfigInfoTable(deviceType,classes,unitId,unitName){
	Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayUnitCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==2){
				Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(unitName+'/计算项配置');
			}else{
				Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle('计算项配置');
			}
			if(protocolDisplayUnitCalItemsConfigHandsontableHelper==null || protocolDisplayUnitCalItemsConfigHandsontableHelper.hot==undefined){
				protocolDisplayUnitCalItemsConfigHandsontableHelper = ProtocolDisplayUnitCalItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitCalItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','显示级别','显示顺序','实时曲线顺序','实时曲线颜色','历史曲线顺序','历史曲线颜色','']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}}," 
						+"{data:'isRealtimeCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeCurveColor'},"
						+"{data:'isHistoryCurve',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCalItemsConfigHandsontableHelper);}},"
						+"{data:'historyCurveColor'},"
						+"{data:'code'}"
						+"]";
				protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayUnitCalItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayUnitCalItemsConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType:deviceType,
			classes:classes,
			unitId:unitId
        }
	});
};

var ProtocolDisplayUnitCalItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitCalItemsConfigHandsontableHelper = {};
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.hot1 = '';
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.divid = divid;
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.columns=[];
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitCalItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitCalItemsConfigHandsontableHelper.divid);
	        	protocolDisplayUnitCalItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [10],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,50,140,80,60,60,85,85,85,85],
	                columns:protocolDisplayUnitCalItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayUnitCalItemsConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                	if(ScadaDriverModbusConfigSelectRow!=''){
	                		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                		if(selectedItem.data.classes!=2){
	                			cellProperties.readOnly = true;
	                		}else{
	                			if (visualColIndex >=1 && visualColIndex<=3) {
	    							cellProperties.readOnly = true;
	    		                }else if(visualColIndex==7||visualColIndex==9){
	    		                	cellProperties.renderer = protocolDisplayUnitCalItemsConfigHandsontableHelper.addCurveBg;
	    		                }
	                		}
	                	}
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	var row1=protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getDataAtRow(row);
	                	if(row1[0] && (column==7||column==9)){
	                		var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                		if(ScadaDriverModbusConfigSelectRow!=''){
	                			var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                			if(selectedItem.data.classes==2){
	                				var CurveColorSelectWindow=Ext.create("AP.view.acquisitionUnit.CurveColorSelectWindow");
	                				Ext.getCmp("curveColorSelectedTableType_Id").setValue(1);//计算项表
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
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        //保存数据
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitCalItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitCalItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitCalItemsConfigHandsontableHelper;
	    }
};

function CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(protocolName,classes,code,unitId,acqUnitId,unitName){
	Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayUnitCtrlItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(classes==2){
				Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(unitName+'/控制项配置');
			}else{
				Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle('控制项配置');
			}
			if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper==null || protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot==undefined){
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper = ProtocolDisplayUnitCtrlItemsConfigHandsontableHelper.createNew("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','显示级别','显示顺序']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCtrlItemsConfigHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitCtrlItemsConfigHandsontableHelper);}}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}," 
						+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolDisplayUnitCtrlItemsConfigHandsontableHelper);}},"
						+"{data:'bitIndex'}"
						+"]";
				
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code,
			unitId:unitId,
			acqUnitId:acqUnitId
        }
	});
};

var ProtocolDisplayUnitCtrlItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitCtrlItemsConfigHandsontableHelper = {};
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot1 = '';
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.divid = divid;
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns=[];
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitCtrlItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitCtrlItemsConfigHandsontableHelper.divid);
	        	protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [6,7,8],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [25,50,140,80,60,60],
	                columns:protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	                	if(ScadaDriverModbusConfigSelectRow!=''){
	                		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                		if(selectedItem.data.classes!=2){
	                			cellProperties.readOnly = true;
	                		}else{
	                			if (visualColIndex >=1 && visualColIndex<=3) {
	    							cellProperties.readOnly = true;
	    		                }
	                		}
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
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitCtrlItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitCtrlItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
	    }
};

function CreateProtocolDisplayUnitConfigPropertiesInfoTable(data){
	var root=[];
	if(data.classes==2){
		var item1={};
		item1.id=1;
		item1.title='单元名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='采控单元';
		item2.value=data.acqUnitName;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='备注';
		item3.value=data.remark;
		root.push(item3);
	}
	
	if(protocolDisplayUnitPropertiesHandsontableHelper==null || protocolDisplayUnitPropertiesHandsontableHelper.hot==undefined){
		protocolDisplayUnitPropertiesHandsontableHelper = ProtocolDisplayUnitPropertiesHandsontableHelper.createNew("ModbusProtocolDisplayUnitPropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolDisplayUnitPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolDisplayUnitPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolDisplayUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolDisplayUnitPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolDisplayUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolDisplayUnitPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolDisplayUnitPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayUnitPropertiesHandsontableHelper = {};
	        protocolDisplayUnitPropertiesHandsontableHelper.hot = '';
	        protocolDisplayUnitPropertiesHandsontableHelper.classes =null;
	        protocolDisplayUnitPropertiesHandsontableHelper.divid = divid;
	        protocolDisplayUnitPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayUnitPropertiesHandsontableHelper.colHeaders=[];
	        protocolDisplayUnitPropertiesHandsontableHelper.columns=[];
	        protocolDisplayUnitPropertiesHandsontableHelper.AllData=[];
	        
	        protocolDisplayUnitPropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayUnitPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayUnitPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayUnitPropertiesHandsontableHelper.divid);
	        	protocolDisplayUnitPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,3,5],
	                columns:protocolDisplayUnitPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayUnitPropertiesHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = protocolDisplayUnitPropertiesHandsontableHelper.addBoldBg;
		                }else if(visualColIndex === 2 && visualRowIndex===0){
	                    	this.validator=function (val, callback) {
	                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolDisplayUnitPropertiesHandsontableHelper);
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        protocolDisplayUnitPropertiesHandsontableHelper.saveData = function () {}
	        protocolDisplayUnitPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolDisplayUnitPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayUnitPropertiesHandsontableHelper;
	    }
};


function SaveModbusProtocolDisplayUnitConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolDisplayUnitPropertiesHandsontableHelper.hot.getData();
		var displayUnitProperties={};
		if(selectedItem.data.classes==2){//选中的是单元
			displayUnitProperties.classes=selectedItem.data.classes;
			displayUnitProperties.id=selectedItem.data.id;
			displayUnitProperties.unitCode=selectedItem.data.code;
			displayUnitProperties.unitName=propertiesData[0][2];
			displayUnitProperties.acqUnitId=selectedItem.data.acqUnitId;
			displayUnitProperties.acqUnitName=propertiesData[1][2];
			displayUnitProperties.remark=propertiesData[2][2];
		}
		if(selectedItem.data.classes==2){//保存单元
			var displayUnitSaveData={};
			displayUnitSaveData.updatelist=[];
			displayUnitSaveData.updatelist.push(displayUnitProperties);
			saveDisplayUnitConfigData(displayUnitSaveData,selectedItem.data.protocol,selectedItem.parentNode.data.deviceType);
			grantDisplayAcqItemsPermission();
			grantDisplayCalItemsPermission();
			grantDisplayCtrlItemsPermission();
		}
	}
};

function saveDisplayUnitConfigData(displayUnitSaveData,protocol,deviceType){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveDisplayUnitHandsontableData',
		success:function(response) {
			rdata=Ext.JSON.decode(response.responseText);
			if (rdata.success) {
            	Ext.MessageBox.alert("信息","保存成功");
            	Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert("信息","采控单元数据保存失败");
            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
            displayUnitConfigHandsontableHelper.clearContainer();
		},
		params: {
        	data: JSON.stringify(displayUnitSaveData),
        	protocol: protocol,
        	deviceType:deviceType
        }
	});
}

var grantDisplayAcqItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitAcqItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
    var acqItemsData = protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantAcqItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(acqItemsData, function (name, index, countriesItSelf) {
        if ((acqItemsData[index][0]+'')==='true') {
        	var itemName = acqItemsData[index][2];
        	
        	var itemShowLevel = acqItemsData[index][4];
        	var itemSort = acqItemsData[index][5];
        	var isRealtimeCurve=acqItemsData[index][6];
        	var realtimeCurveColor=acqItemsData[index][7];
        	var isHistoryCurve=acqItemsData[index][8];
        	var historyCurveColor=acqItemsData[index][9];
        	
        	var resolutionMode = acqItemsData[index][10];
        	var itemAddr = acqItemsData[index][11];
        	var bitIndex=acqItemsData[index][12];
            
            addjson.push(itemName);
            addItemSort.push(itemSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + ":"
            + itemSort+ ":"
            + itemShowLevel+ ":" 
            + isRealtimeCurve+ ":" 
            + realtimeCurveColor+ ":" 
            + isHistoryCurve + ":" 
            + historyCurveColor + ":" 
            
            + resolutionMode+ ":"
            + itemAddr + ":" 
            + bitIndex +":"
            
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
            protocol :protocol,
            unitCode: unitCode,
            unitId:unitId,
            itemType:0,
            matrixCodes: matrixCodes_
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(cosog.string.ts, "<font color=blue>保存成功</font>");
            }
            if (result.msg == false) {
                Ext.Msg.alert('info', "<font color=red>SORRY！" + '采集项安排失败' + "。</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
    return false;
}

var grantDisplayCalItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitCalItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
    var calItemsData = protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantCalItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(calItemsData, function (name, index, countriesItSelf) {
        if ((calItemsData[index][0]+'')==='true') {
        	var itemName = calItemsData[index][2];
        	
        	var itemShowLevel = calItemsData[index][4];
        	var itemSort = calItemsData[index][5];
        	var isRealtimeCurve=calItemsData[index][6];
        	var realtimeCurveColor=calItemsData[index][7];
        	var isHistoryCurve=calItemsData[index][8];
        	var historyCurveColor=calItemsData[index][9];
        	
        	var itemCode = calItemsData[index][10];
        	
            addjson.push(itemCode);
            addItemSort.push(itemSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + ":"
            + itemCode+ ":"
            + itemSort+ ":"
            + itemShowLevel+ ":" 
            + isRealtimeCurve+ ":" 
            + realtimeCurveColor+ ":" 
            + isHistoryCurve + ":" 
            + historyCurveColor + ":" 
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
            protocol :protocol,
            unitCode: unitCode,
            unitId:unitId,
            itemType:1,
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

var grantDisplayCtrlItemsPermission = function () {
	var DisplayUnitConfigTreeSelectRow= Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue();
	if (protocolDisplayUnitCtrlItemsConfigHandsontableHelper == null ||DisplayUnitConfigTreeSelectRow=='') {
        return false;
    }
	var selectedItem=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().getAt(DisplayUnitConfigTreeSelectRow);
    var ctrlItemsData = protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantCtrlItemsToDisplayUnitPermission'
    // 添加条件
    var addjson = [];
    var addItemSort=[];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    
    var unitCode = selectedItem.data.code;
    var unitId = selectedItem.data.id;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(unitCode)) {
        return false
    }

    Ext.Array.each(ctrlItemsData, function (name, index, countriesItSelf) {
        if ((ctrlItemsData[index][0]+'')==='true') {
        	var itemName = ctrlItemsData[index][2];
        	
        	var itemShowLevel = ctrlItemsData[index][4];
        	var itemSort = ctrlItemsData[index][5];
        	
        	var resolutionMode = ctrlItemsData[index][6];
        	var itemAddr = ctrlItemsData[index][7];
        	var bitIndex=ctrlItemsData[index][8];
            
            addjson.push(itemName);
            addItemSort.push(itemSort);
            var matrix_value = '0,0,0';
            matrixData += itemName + ":"
            + itemSort+ ":"
            + itemShowLevel+ ":" 
            
            + resolutionMode+ ":"
            + itemAddr + ":" 
            + bitIndex +":"
            
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
            protocol :protocol,
            unitCode: unitCode,
            unitId:unitId,
            itemType:2,
            matrixCodes: matrixCodes_
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(cosog.string.ts, "<font color=blue>保存成功</font>");
            }
            if (result.msg == false) {
                Ext.Msg.alert('info', "<font color=red>SORRY！" + '控制项安排失败' + "。</font>");
            }
        },
        failure: function () {
            Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });

    return false;
}
