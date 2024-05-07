var protocolDisplayInstanceAcqItemsHandsontableHelper=null;
var protocolDisplayInstanceCalItemsHandsontableHelper=null;
var protocolDisplayInstanceCtrlItemsHandsontableHelper=null;
var protocolDisplayInstanceInputItemsHandsontableHelper=null;
var protocolDisplayInstancePropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolDisplayInstanceConfigInfoView',
    layout: "fit",
    id:'modbusProtocolDisplayInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolDisplayInstanceTreeSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加实例',
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolDisplayInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolDisplayInstanceConfigTreeData();
        			}
                }],
                layout: "border",
                items: [{
                	border: true,
                	region: 'west',
                	width:'25%',
                    layout: "border",
                    border: true,
                    header: false,
                    split: true,
                    collapsible: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'显示实例列表',
                    	layout: 'fit',
                    	id:"ModbusProtocolDisplayInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ProtocolDisplayInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolDisplayInstancePropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolDisplayInstancePropertiesHandsontableHelper!=null && protocolDisplayInstancePropertiesHandsontableHelper.hot!=undefined){
//                            		protocolDisplayInstancePropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolDisplayInstancePropertiesHandsontableHelper.hot.updateSettings({
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
                    		title:'采集项',
                    		id:"ProtocolDisplayInstanceAcqItemsConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ProtocolDisplayInstanceAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolDisplayInstanceAcqItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolDisplayInstanceAcqItemsHandsontableHelper!=null && protocolDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined){
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolDisplayInstanceAcqItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	},{
                    		region: 'south',
                        	height:'50%',
                        	title:'控制项',
                    		id:"ProtocolDisplayInstanceCtrlItemsConfigTableInfoPanel_Id",
                            layout: 'fit',
                            collapsible: true,
                            split: true,
                            html:'<div class="ProtocolDisplayInstanceCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolDisplayInstanceCtrlItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolDisplayInstanceCtrlItemsHandsontableHelper!=null && protocolDisplayInstanceCtrlItemsHandsontableHelper.hot!=undefined){
//                                		protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	}]
                	},{
                		region: 'east',
                		width:'50%',
                		layout: "border",
                		items: [{
                			region: 'center',
                			layout: 'fit',
                			title:'录入项',
                    		id:"ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id",
                            html:'<div class="ProtocolDisplayInstanceInputItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolDisplayInstanceInputItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolDisplayInstanceInputItemsHandsontableHelper!=null && protocolDisplayInstanceInputItemsHandsontableHelper.hot!=undefined){
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolDisplayInstanceInputItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                		},{
                    		region: 'south',
                        	height:'50%',
                        	layout: 'fit',
                            collapsible: true,
                            split: true,
                			title:'计算项',
                        	id:"ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id",
                            html:'<div class="ProtocolDisplayInstanceCalItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolDisplayInstanceCalItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolDisplayInstanceCalItemsHandsontableHelper!=null && protocolDisplayInstanceCalItemsHandsontableHelper.hot!=undefined){
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolDisplayInstanceCalItemsHandsontableHelper.hot.updateSettings({
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

function CreateProtocolDisplayInstancePropertiesInfoTable(data){
	var root=[];
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title='根节点';
		item1.value='实例列表';
		root.push(item1);
	}else if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title='实例名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='显示单元';
		item2.value=data.displayUnitName;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='排序序号';
		item3.value=data.sort;
		root.push(item3);
	}else if(data.classes==2){
		var item1={};
		item1.id=1;
		item1.title='显示单元';
		item1.value=data.text;
		root.push(item1);
	}
	
	if(protocolDisplayInstancePropertiesHandsontableHelper==null || protocolDisplayInstancePropertiesHandsontableHelper.hot==undefined){
		protocolDisplayInstancePropertiesHandsontableHelper = ProtocolDisplayInstancePropertiesHandsontableHelper.createNew("ProtocolDisplayInstancePropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolDisplayInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolDisplayInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolDisplayInstancePropertiesHandsontableHelper.classes=data.classes;
		protocolDisplayInstancePropertiesHandsontableHelper.createTable(root);
	}else{
		protocolDisplayInstancePropertiesHandsontableHelper.classes=data.classes;
		protocolDisplayInstancePropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolDisplayInstancePropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayInstancePropertiesHandsontableHelper = {};
	        protocolDisplayInstancePropertiesHandsontableHelper.hot = '';
	        protocolDisplayInstancePropertiesHandsontableHelper.classes =null;
	        protocolDisplayInstancePropertiesHandsontableHelper.divid = divid;
	        protocolDisplayInstancePropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayInstancePropertiesHandsontableHelper.colHeaders=[];
	        protocolDisplayInstancePropertiesHandsontableHelper.columns=[];
	        protocolDisplayInstancePropertiesHandsontableHelper.AllData=[];
	        
	        protocolDisplayInstancePropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayInstancePropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstancePropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstancePropertiesHandsontableHelper.divid);
	        	protocolDisplayInstancePropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,5,5],
	                columns:protocolDisplayInstancePropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayInstancePropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    	if (visualColIndex ==0 || visualColIndex ==1) {
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg;
			                }
		                    if(protocolDisplayInstancePropertiesHandsontableHelper.classes===0 || protocolDisplayInstancePropertiesHandsontableHelper.classes===2){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolDisplayInstancePropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolDisplayInstancePropertiesHandsontableHelper);
			                    	}
			                    }else if(visualColIndex === 2 && visualRowIndex===2){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolDisplayInstancePropertiesHandsontableHelper);
			                    	}
			                    }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        protocolDisplayInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolDisplayInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolDisplayInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolDisplayInstancePropertiesHandsontableHelper;
	    }
};

function SaveModbusProtocolDisplayInstanceConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolDisplayInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.name=propertiesData[0][2];
			saveData.displayUnitId=selectedItem.data.displayUnitId;
			saveData.sort=propertiesData[2][2];
			SaveModbusProtocolDisplayInstanceData(saveData);
		}
	}
};

function SaveModbusProtocolDisplayInstanceData(saveData){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolDisplayInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.MessageBox.alert("信息","保存成功");
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id").getStore().load();
            	
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

function CreateProtocolDisplayInstanceAcqItemsInfoTable(id,instanceName,classes){
	Ext.getCmp("ProtocolDisplayInstanceAcqItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceAcqItemsConfigData',
		success:function(response) {
			Ext.getCmp("ProtocolDisplayInstanceAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("ProtocolDisplayInstanceAcqItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/采集项");
			}else{
				Ext.getCmp("ProtocolDisplayInstanceAcqItemsConfigTableInfoPanel_Id").setTitle("采集项");
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolDisplayInstanceAcqItemsHandsontableHelper==null || protocolDisplayInstanceAcqItemsHandsontableHelper.hot==undefined){
				protocolDisplayInstanceAcqItemsHandsontableHelper = ProtocolDisplayInstanceAcqItemsHandsontableHelper.createNew("ProtocolDisplayInstanceAcqItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序','实时曲线','历史曲线']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				protocolDisplayInstanceAcqItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceAcqItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolDisplayInstanceAcqItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceAcqItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolDisplayInstanceAcqItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceAcqItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ProtocolDisplayInstanceAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes
        }
	});
};

var ProtocolDisplayInstanceAcqItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayInstanceAcqItemsHandsontableHelper = {};
	        protocolDisplayInstanceAcqItemsHandsontableHelper.hot1 = '';
	        protocolDisplayInstanceAcqItemsHandsontableHelper.divid = divid;
	        protocolDisplayInstanceAcqItemsHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayInstanceAcqItemsHandsontableHelper.colHeaders=[];
	        protocolDisplayInstanceAcqItemsHandsontableHelper.columns=[];
	        protocolDisplayInstanceAcqItemsHandsontableHelper.AllData=[];
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceAcqItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceAcqItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60,85,85],
	                columns:protocolDisplayInstanceAcqItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayInstanceAcqItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==5||visualColIndex==6){
		                	cellProperties.renderer = protocolDisplayInstanceAcqItemsHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return protocolDisplayInstanceAcqItemsHandsontableHelper;
	    }
};

function CreateProtocolDisplayInstanceCalItemsInfoTable(id,instanceName,classes,deviceType){
	Ext.getCmp("ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/计算项");
			}else{
				Ext.getCmp("ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id").setTitle("计算项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolDisplayInstanceCalItemsHandsontableHelper==null || protocolDisplayInstanceCalItemsHandsontableHelper.hot==undefined){
				protocolDisplayInstanceCalItemsHandsontableHelper = ProtocolDisplayInstanceCalItemsHandsontableHelper.createNew("ProtocolDisplayInstanceCalItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序','实时曲线','历史曲线','数据来源']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'},"
						+"{data:'dataSource'}"
						+"]";
				protocolDisplayInstanceCalItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceCalItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolDisplayInstanceCalItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceCalItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolDisplayInstanceCalItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceCalItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			deviceType:deviceType
        }
	});
};

var ProtocolDisplayInstanceCalItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayInstanceCalItemsHandsontableHelper = {};
	        protocolDisplayInstanceCalItemsHandsontableHelper.hot1 = '';
	        protocolDisplayInstanceCalItemsHandsontableHelper.divid = divid;
	        protocolDisplayInstanceCalItemsHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayInstanceCalItemsHandsontableHelper.colHeaders=[];
	        protocolDisplayInstanceCalItemsHandsontableHelper.columns=[];
	        protocolDisplayInstanceCalItemsHandsontableHelper.AllData=[];
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceCalItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceCalItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceCalItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60,85,85,85],
	                columns:protocolDisplayInstanceCalItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayInstanceCalItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==5||visualColIndex==6){
		                	cellProperties.renderer = protocolDisplayInstanceCalItemsHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return protocolDisplayInstanceCalItemsHandsontableHelper;
	    }
};

function CreateProtocolDisplayInstanceInputItemsInfoTable(id,instanceName,classes,deviceType){
	Ext.getCmp("ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceInputItemsConfigData',
		success:function(response) {
			Ext.getCmp("ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/录入项");
			}else{
				Ext.getCmp("ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id").setTitle("录入项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolDisplayInstanceInputItemsHandsontableHelper==null || protocolDisplayInstanceInputItemsHandsontableHelper.hot==undefined){
				protocolDisplayInstanceInputItemsHandsontableHelper = ProtocolDisplayInstanceInputItemsHandsontableHelper.createNew("ProtocolDisplayInstanceInputItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序','实时曲线','历史曲线']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				protocolDisplayInstanceInputItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceInputItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolDisplayInstanceInputItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceInputItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolDisplayInstanceInputItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceInputItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			deviceType:deviceType
        }
	});
};

var ProtocolDisplayInstanceInputItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayInstanceInputItemsHandsontableHelper = {};
	        protocolDisplayInstanceInputItemsHandsontableHelper.hot1 = '';
	        protocolDisplayInstanceInputItemsHandsontableHelper.divid = divid;
	        protocolDisplayInstanceInputItemsHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayInstanceInputItemsHandsontableHelper.colHeaders=[];
	        protocolDisplayInstanceInputItemsHandsontableHelper.columns=[];
	        protocolDisplayInstanceInputItemsHandsontableHelper.AllData=[];
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceInputItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceInputItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceInputItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60,85,85],
	                columns:protocolDisplayInstanceInputItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayInstanceInputItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==5||visualColIndex==6){
		                	cellProperties.renderer = protocolDisplayInstanceInputItemsHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return protocolDisplayInstanceInputItemsHandsontableHelper;
	    }
};

function CreateProtocolDisplayInstanceCtrlItemsInfoTable(id,instanceName,classes){
	Ext.getCmp("ProtocolDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceCtrlItemsConfigData',
		success:function(response) {
			Ext.getCmp("ProtocolDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(instanceName!=''){
				Ext.getCmp("ProtocolDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").setTitle(instanceName+"/控制项");
			}else{
				Ext.getCmp("ProtocolDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").setTitle("控制项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolDisplayInstanceCtrlItemsHandsontableHelper==null || protocolDisplayInstanceCtrlItemsHandsontableHelper.hot==undefined){
				protocolDisplayInstanceCtrlItemsHandsontableHelper = ProtocolDisplayInstanceCtrlItemsHandsontableHelper.createNew("ProtocolDisplayInstanceCtrlItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel'}," 
						+"{data:'sort'}"
						+"]";
				protocolDisplayInstanceCtrlItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceCtrlItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolDisplayInstanceCtrlItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceCtrlItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ProtocolDisplayInstanceCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes
        }
	});
};

var ProtocolDisplayInstanceCtrlItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolDisplayInstanceCtrlItemsHandsontableHelper = {};
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.hot1 = '';
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.divid = divid;
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.validresult=true;//数据校验
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.colHeaders=[];
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.columns=[];
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.AllData=[];
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceCtrlItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceCtrlItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceCtrlItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60],
	                columns:protocolDisplayInstanceCtrlItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolDisplayInstanceCtrlItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return protocolDisplayInstanceCtrlItemsHandsontableHelper;
	    }
};