//var protocolDisplayInstanceAcqItemsHandsontableHelper=null;
//var protocolDisplayInstanceCalItemsHandsontableHelper=null;
//var protocolDisplayInstanceCtrlItemsHandsontableHelper=null;
//var protocolDisplayInstanceInputItemsHandsontableHelper=null;
//var protocolDisplayInstancePropertiesHandsontableHelper=null;
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
                },"-",{
                	xtype: 'button',
        			text: '导出',
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolDisplayInstanceWindow");
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: '导入',
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'import',
        			handler: function (v, o) {
        				var selectedDeviceTypeName="";
        				var selectedDeviceTypeId="";
        				var tabTreeStore = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getStore();
        				var count=tabTreeStore.getCount();
        				var tabTreeSelection = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        				var rec=null;
        				if (tabTreeSelection.length > 0) {
        					rec=tabTreeSelection[0];
        					selectedDeviceTypeName=foreachAndSearchTabAbsolutePath(tabTreeStore.data.items,tabTreeSelection[0].data.deviceTypeId);
        					selectedDeviceTypeId=tabTreeSelection[0].data.deviceTypeId;
        				} else {
        					if(count>0){
        						rec=orgTreeStore.getAt(0);
        						selectedDeviceTypeName=orgTreeStore.getAt(0).data.text;
        						selectedDeviceTypeId=orgTreeStore.getAt(0).data.deviceTypeId;
        					}
        				}
        				var window = Ext.create("AP.view.acquisitionUnit.ImportDisplayInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportDisplayInstanceWinTabLabel_Id").setHtml("实例将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,请确认<br/>&nbsp;");
//        			    Ext.getCmp("ImportDisplayInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportDisplayInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
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
                		header: false,
                		split: true,
                        collapsible: true,
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
	        
	        protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstancePropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
		                    	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg;
				                }else{
				                	cellProperties.renderer = protocolDisplayInstancePropertiesHandsontableHelper.addCellStyle;
				                }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayInstancePropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayInstancePropertiesHandsontableHelper!=null
	                		&& protocolDisplayInstancePropertiesHandsontableHelper.hot!=''
	                		&& protocolDisplayInstancePropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayInstancePropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
				var colHeaders = "[" 
                	+"['','','','',{label: '实时监控', colspan: 4},{label: '历史查询', colspan: 4}]," 
                	+"['','','','',{label: '动态数据', colspan: 3},'趋势曲线',{label: '历史数据', colspan: 3},'趋势曲线']," 
                	+"['序号','名称','单位','显示级别'," 
                	+"'字段顺序','前景色','背景色','曲线配置'," 
                	+"'字段顺序','前景色','背景色','曲线配置']"
                	+"]";
				
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}},"
						+"{data:'realtimeColor'}," 
	                    +"{data:'realtimeBgColor'}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'historyColor'}," 
	                    +"{data:'historyBgColor'}," 
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				protocolDisplayInstanceAcqItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceAcqItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayInstanceAcqItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayInstanceAcqItemsHandsontableHelper.hot.loadData(result.totalRoot);
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
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }else{
	            	td.style.backgroundColor = 'rgb(251, 251, 251)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }else{
	            	td.style.backgroundColor = 'rgb(251, 251, 251)';
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayInstanceAcqItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayInstanceAcqItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayInstanceAcqItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayInstanceAcqItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayInstanceAcqItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceAcqItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceAcqItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceAcqItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80],
	                columns:protocolDisplayInstanceAcqItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolDisplayInstanceAcqItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolDisplayInstanceAcqItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==7||visualColIndex==11){
		                	cellProperties.renderer = protocolDisplayInstanceAcqItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = protocolDisplayInstanceAcqItemsHandsontableHelper.addCellBgColor;
                        }else{
		                	cellProperties.renderer = protocolDisplayInstanceAcqItemsHandsontableHelper.addReadOnlyBg;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayInstanceAcqItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayInstanceAcqItemsHandsontableHelper!=null
	                		&& protocolDisplayInstanceAcqItemsHandsontableHelper.hot!=''
	                		&& protocolDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayInstanceAcqItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayInstanceAcqItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolDisplayInstanceAcqItemsHandsontableHelper;
	    }
};

function CreateProtocolDisplayInstanceCalItemsInfoTable(id,instanceName,classes,calculateType){
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
				
				var colHeaders="[" 
					+"['','','','',{label: '实时监控', colspan: 4},{label: '历史查询', colspan: 4},'']," 
					+"['','','','',{label: '动态数据', colspan: 3},'趋势曲线',{label: '历史数据', colspan: 3},'趋势曲线','']," 
					+"['序号','名称','单位','显示级别'," 
					+"'字段顺序','前景色','背景色','曲线配置'," 
					+"'字段顺序','前景色','背景色','曲线配置'," 
					+"'数据来源']" 
					+"]";
				
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
						+"{data:'realtimeColor'}," 
	                    +"{data:'realtimeBgColor'}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceCalItemsHandsontableHelper);}}," 
						+"{data:'historyColor'}," 
	                    +"{data:'historyBgColor'}," 
						+"{data:'historyCurveConfShowValue'},"
						+"{data:'dataSource'}"
						+"]";
				protocolDisplayInstanceCalItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceCalItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayInstanceCalItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayInstanceCalItemsHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ProtocolDisplayInstanceCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			calculateType:calculateType
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
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }else{
	            	td.style.backgroundColor = 'rgb(251, 251, 251)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }else{
	            	td.style.backgroundColor = 'rgb(251, 251, 251)';
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayInstanceCalItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayInstanceCalItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayInstanceCalItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayInstanceCalItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayInstanceCalItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCalItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceCalItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceCalItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceCalItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80,80],
	                columns:protocolDisplayInstanceCalItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolDisplayInstanceCalItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolDisplayInstanceCalItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==7||visualColIndex==11){
		                	cellProperties.renderer = protocolDisplayInstanceCalItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = protocolDisplayInstanceCalItemsHandsontableHelper.addCellBgColor;
                        }else{
                        	cellProperties.renderer = protocolDisplayInstanceCalItemsHandsontableHelper.addReadOnlyBg;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayInstanceCalItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayInstanceCalItemsHandsontableHelper!=null
	                		&& protocolDisplayInstanceCalItemsHandsontableHelper.hot!=''
	                		&& protocolDisplayInstanceCalItemsHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayInstanceCalItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayInstanceCalItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolDisplayInstanceCalItemsHandsontableHelper;
	    }
};

function CreateProtocolDisplayInstanceInputItemsInfoTable(id,instanceName,classes,calculateType){
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
				var colHeaders="[" 
					+"['','','','',{label: '实时监控', colspan: 4},{label: '历史查询', colspan: 4}]," 
					+"['','','','',{label: '动态数据', colspan: 3},'趋势曲线',{label: '历史数据', colspan: 3},'趋势曲线']," 
					+"['序号','名称','单位','显示级别'," 
					+"'字段顺序','前景色','背景色','曲线配置'," 
					+"'字段顺序','前景色','背景色','曲线配置']" 
					+"]";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
						+"{data:'realtimeColor'}," 
	                    +"{data:'realtimeBgColor'}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayInstanceInputItemsHandsontableHelper);}}," 
						+"{data:'historyColor'}," 
	                    +"{data:'historyBgColor'}," 
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				protocolDisplayInstanceInputItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceInputItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayInstanceInputItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayInstanceInputItemsHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ProtocolDisplayInstanceInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			calculateType:calculateType
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
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null && value!=""){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }else{
	            	td.style.backgroundColor = 'rgb(251, 251, 251)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addCellBgColor = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value!=null && value!="") {
	                td.style.backgroundColor = '#' + value;
	            }else{
	            	td.style.backgroundColor = 'rgb(251, 251, 251)';
	            }
	            td.style.whiteSpace = 'nowrap'; //文本不换行
	            td.style.overflow = 'hidden'; //超出部分隐藏
	            td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayInstanceInputItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayInstanceInputItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayInstanceInputItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayInstanceInputItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayInstanceInputItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceInputItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolDisplayInstanceInputItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolDisplayInstanceInputItemsHandsontableHelper.divid);
	        	protocolDisplayInstanceInputItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,80,80,80,80,80,80,80,80],
	                columns:protocolDisplayInstanceInputItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolDisplayInstanceInputItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolDisplayInstanceInputItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==7||visualColIndex==11){
		                	cellProperties.renderer = protocolDisplayInstanceInputItemsHandsontableHelper.addCurveBg;
		                }else if (visualColIndex == 5 || visualColIndex == 6 || visualColIndex == 9 || visualColIndex == 10) {
                            cellProperties.renderer = protocolDisplayInstanceInputItemsHandsontableHelper.addCellBgColor;
                        }else{
                        	cellProperties.renderer = protocolDisplayInstanceInputItemsHandsontableHelper.addReadOnlyBg;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayInstanceInputItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayInstanceInputItemsHandsontableHelper!=null
	                		&& protocolDisplayInstanceInputItemsHandsontableHelper.hot!=''
	                		&& protocolDisplayInstanceInputItemsHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayInstanceInputItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayInstanceInputItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
				var colHeaders="['序号','名称','单位','显示级别','字段顺序']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel'}," 
						+"{data:'realtimeSort'}"
						+"]";
				protocolDisplayInstanceCtrlItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstanceCtrlItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayInstanceCtrlItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.loadData(result.totalRoot);
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
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolDisplayInstanceCtrlItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolDisplayInstanceCtrlItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolDisplayInstanceCtrlItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolDisplayInstanceCtrlItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolDisplayInstanceCtrlItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	        }
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolDisplayInstanceCtrlItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
	                    cellProperties.renderer = protocolDisplayInstanceCtrlItemsHandsontableHelper.addReadOnlyBg;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolDisplayInstanceCtrlItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolDisplayInstanceCtrlItemsHandsontableHelper!=null
	                		&& protocolDisplayInstanceCtrlItemsHandsontableHelper.hot!=''
	                		&& protocolDisplayInstanceCtrlItemsHandsontableHelper.hot!=undefined 
	                		&& protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolDisplayInstanceCtrlItemsHandsontableHelper;
	    }
};