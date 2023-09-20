var protocolAcqUnitConfigItemsHandsontableHelper=null;
var protocolConfigAcqUnitPropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolAcqUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolAcqUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolAcqUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolAcqGroupConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加采控单元',
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addAcquisitionUnitInfo();
        			}
        		},"-",{
        			xtype: 'button',
                    text: '添加采控组',
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addAcquisitionGroupInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAcqUnitConfigTreeData();
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'25%',
                    layout: "border",
                    border: true,
                    header: false,
                    split: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'采控单元配置',
                    	layout: 'fit',
                    	id:"ModbusProtocolAcqGroupConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolAcqGroupPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAcqGroupPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolConfigAcqUnitPropertiesHandsontableHelper!=null && protocolConfigAcqUnitPropertiesHandsontableHelper.hot!=undefined){
//                            		protocolConfigAcqUnitPropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolConfigAcqUnitPropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    }]
                },{
                	border: true,
//                    flex: 4,
                	region: 'center',
                    title:'采控项配置',
                    id:"ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id",
                    layout: 'fit',
                    html:'<div class="ModbusProtocolAcqGroupItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAcqGroupItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(protocolAcqUnitConfigItemsHandsontableHelper!=null && protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
//                        		protocolAcqUnitConfigItemsHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		protocolAcqUnitConfigItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateProtocolAcqUnitItemsConfigInfoTable(protocolName,classes,code,type){
	Ext.getCmp("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAcqUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAcqUnitConfigItemsHandsontableHelper==null || protocolAcqUnitConfigItemsHandsontableHelper.hot==undefined){
				protocolAcqUnitConfigItemsHandsontableHelper = ProtocolAcqUnitConfigItemsHandsontableHelper.createNew("ModbusProtocolAcqGroupItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','起始地址(十进制)','读写类型','单位','解析模式']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAcqUnitConfigItemsHandsontableHelper);}},"
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
						+"{data:'unit'},"
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}," 
						+"{data:'bitIndex'}"
						+"]";
				
				protocolAcqUnitConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAcqUnitConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolAcqUnitConfigItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolAcqUnitConfigItemsHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code,
			type:type
        }
	});
};

var ProtocolAcqUnitConfigItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAcqUnitConfigItemsHandsontableHelper = {};
	        protocolAcqUnitConfigItemsHandsontableHelper.hot1 = '';
	        protocolAcqUnitConfigItemsHandsontableHelper.divid = divid;
	        protocolAcqUnitConfigItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAcqUnitConfigItemsHandsontableHelper.colHeaders=[];
	        protocolAcqUnitConfigItemsHandsontableHelper.columns=[];
	        protocolAcqUnitConfigItemsHandsontableHelper.AllData=[];
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	td.style.backgroundColor = '#'+value;
	            }
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAcqUnitConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAcqUnitConfigItemsHandsontableHelper.divid);
	        	protocolAcqUnitConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [7],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,50,140,60,80,80,80],
	                columns:protocolAcqUnitConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAcqUnitConfigItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue();
	                	if(ScadaDriverModbusConfigSelectRow!=''){
	                		var selectedItem=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                		if(selectedItem.data.classes!=3){
	                			cellProperties.readOnly = true;
	                		}else{
	                			if (visualColIndex >=1 && visualColIndex<=6) {
	    							cellProperties.readOnly = true;
	    		                }else if(visualColIndex==10||visualColIndex==12){
	    		                	cellProperties.renderer = protocolAcqUnitConfigItemsHandsontableHelper.addCurveBg;
	    		                }
	                		}
	                	}
	                    return cellProperties;
	                },
	                afterBeginEditing:function(row,column){
	                	var row1=protocolAcqUnitConfigItemsHandsontableHelper.hot.getDataAtRow(row);
	                	if(row1[0] && (column==10||column==12)){
	                		var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue();
	                		if(ScadaDriverModbusConfigSelectRow!=''){
	                			var selectedItem=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                			if(selectedItem.data.classes==3 && selectedItem.data.type==0){
	                				var CurveColorSelectWindow=Ext.create("AP.view.acquisitionUnit.CurveColorSelectWindow");
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
	        protocolAcqUnitConfigItemsHandsontableHelper.saveData = function () {}
	        protocolAcqUnitConfigItemsHandsontableHelper.clearContainer = function () {
	        	protocolAcqUnitConfigItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAcqUnitConfigItemsHandsontableHelper;
	    }
};


function CreateProtocolAcqUnitConfigPropertiesInfoTable(data){
	var root=[];
	if(data.classes==2){
		var item1={};
		item1.id=1;
		item1.title='单元名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='备注';
		item2.value=data.remark;
		root.push(item2);
	}else if(data.classes==3){
		var item1={};
		item1.id=1;
		item1.title='组名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='组类型';
		item2.value=data.typeName;
		root.push(item2);
		
		if(data.type==0){
			var item3={};
			item3.id=3;
			item3.title='单组定时间隔(s)';
			item3.value=data.groupTimingInterval;
			root.push(item3);
			
			var item4={};
			item4.id=4;
			item4.title='单组入库间隔(s)';
			item4.value=data.groupSavingInterval;
			root.push(item4);
			
			var item5={};
			item5.id=5;
			item5.title='备注';
			item5.value=data.remark;
			root.push(item5);
		}else if(data.type==1){
			var item3={};
			item3.id=3;
			item3.title='备注';
			item3.value=data.remark;
			root.push(item3);
		}
		
	}
	
	if(protocolConfigAcqUnitPropertiesHandsontableHelper==null || protocolConfigAcqUnitPropertiesHandsontableHelper.hot==undefined){
		protocolConfigAcqUnitPropertiesHandsontableHelper = ProtocolConfigAcqUnitPropertiesHandsontableHelper.createNew("ModbusProtocolAcqGroupPropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolConfigAcqUnitPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolConfigAcqUnitPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolConfigAcqUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolConfigAcqUnitPropertiesHandsontableHelper.type=data.type;
		protocolConfigAcqUnitPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolConfigAcqUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolConfigAcqUnitPropertiesHandsontableHelper.type=data.type;
		protocolConfigAcqUnitPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolConfigAcqUnitPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigAcqUnitPropertiesHandsontableHelper = {};
	        protocolConfigAcqUnitPropertiesHandsontableHelper.hot = '';
	        protocolConfigAcqUnitPropertiesHandsontableHelper.classes =null;
	        protocolConfigAcqUnitPropertiesHandsontableHelper.type =null;
	        protocolConfigAcqUnitPropertiesHandsontableHelper.divid = divid;
	        protocolConfigAcqUnitPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolConfigAcqUnitPropertiesHandsontableHelper.colHeaders=[];
	        protocolConfigAcqUnitPropertiesHandsontableHelper.columns=[];
	        protocolConfigAcqUnitPropertiesHandsontableHelper.AllData=[];
	        
	        protocolConfigAcqUnitPropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigAcqUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolConfigAcqUnitPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigAcqUnitPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigAcqUnitPropertiesHandsontableHelper.divid);
	        	protocolConfigAcqUnitPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,5,5],
	                columns:protocolConfigAcqUnitPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigAcqUnitPropertiesHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = protocolConfigAcqUnitPropertiesHandsontableHelper.addBoldBg;
		                }
	                    if(protocolConfigAcqUnitPropertiesHandsontableHelper.classes===1){
	                    	if (visualColIndex === 2 && visualRowIndex===1) {
		                    	this.type = 'dropdown';
		                    	this.source = ['抽油机井','螺杆泵井'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    }else if(protocolConfigAcqUnitPropertiesHandsontableHelper.classes===2){
	                    	if (visualColIndex === 2 && visualRowIndex===0) {
	                    		this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigAcqUnitPropertiesHandsontableHelper);
		                    	}
		                    }
	                    }else if(protocolConfigAcqUnitPropertiesHandsontableHelper.classes===3){
	                    	if (visualColIndex === 2 && visualRowIndex===0) {
	                    		this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigAcqUnitPropertiesHandsontableHelper);
		                    	}
		                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    	this.type = 'dropdown';
		                    	this.source = ['采集组','控制组'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }else if(visualColIndex === 2 && (visualRowIndex===2||visualRowIndex===3) && protocolConfigAcqUnitPropertiesHandsontableHelper.type==0){
		                    	this.validator=function (val, callback) {
		                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolConfigAcqUnitPropertiesHandsontableHelper);
		                    	}
		                    }
	                    	
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        protocolConfigAcqUnitPropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigAcqUnitPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigAcqUnitPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigAcqUnitPropertiesHandsontableHelper;
	    }
};


function SaveModbusProtocolAcqUnitConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolConfigAcqUnitPropertiesHandsontableHelper.hot.getData();
		var protocolProperties={};
		if(selectedItem.data.classes==2){//选中的是采控单元
			protocolProperties.classes=selectedItem.data.classes;
			protocolProperties.id=selectedItem.data.id;
			protocolProperties.unitCode=selectedItem.data.code;
			protocolProperties.unitName=propertiesData[0][2];
			protocolProperties.remark=propertiesData[1][2];
		}else if(selectedItem.data.classes==3){//选中的是采控单元组
			protocolProperties.classes=selectedItem.data.classes;
			protocolProperties.id=selectedItem.data.id;
			protocolProperties.groupCode=selectedItem.data.code;
			protocolProperties.groupName=propertiesData[0][2];
			protocolProperties.typeName=propertiesData[1][2];
			if(selectedItem.data.type==0){//采集组
				protocolProperties.groupTimingInterval=propertiesData[2][2];
				protocolProperties.groupSavingInterval=propertiesData[3][2];
				protocolProperties.remark=propertiesData[4][2];
			}else if(selectedItem.data.type==1){//控制组
//				protocolProperties.groupTimingInterval=propertiesData[2][2];
//				protocolProperties.groupSavingInterval=propertiesData[3][2];
				protocolProperties.remark=propertiesData[2][2];
			}
			
		}
		if(selectedItem.data.classes==2){//保存采控单元
			var acqUnitSaveData={};
			acqUnitSaveData.updatelist=[];
			acqUnitSaveData.updatelist.push(protocolProperties);
			saveAcquisitionUnitConfigData(acqUnitSaveData,selectedItem.data.protocol,selectedItem.parentNode.data.deviceType);
		}
		
		if(selectedItem.data.classes==3){//选中的是采控单元组
			var acqGroupSaveData={};
			acqGroupSaveData.updatelist=[];
			acqGroupSaveData.updatelist.push(protocolProperties);
			
			saveAcquisitionGroupConfigData(acqGroupSaveData,selectedItem.data.protocol,selectedItem.parentNode.data.id);
			//给采控组授予采控项
			grantAcquisitionItemsPermission();
		}
	}
};

function saveModbusProtocolConfigData(configInfo){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveModbusProtocolConfigData',
		success:function(response) {
			var data=Ext.JSON.decode(response.responseText);
			protocolAcqUnitConfigItemsHandsontableHelper.clearContainer();
			if (data.success) {
            	Ext.MessageBox.alert("信息","保存成功");
            	Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert("信息","数据保存失败");
            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			data:JSON.stringify(configInfo)
        }
	});
}

function saveAcquisitionUnitConfigData(acqUnitSaveData,protocol,deviceType){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveAcquisitionUnitHandsontableData',
		success:function(response) {
			rdata=Ext.JSON.decode(response.responseText);
			if (rdata.success) {
            	Ext.MessageBox.alert("信息","保存成功");
            	if(acqUnitSaveData.delidslist!=undefined && acqUnitSaveData.delidslist.length>0){//如果删除
            		Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(0);
            	}
            	Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert("信息","采控单元数据保存失败");
            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
            acquisitionUnitConfigHandsontableHelper.clearContainer();
		},
		params: {
        	data: JSON.stringify(acqUnitSaveData),
        	protocol: protocol,
        	deviceType:deviceType
        }
	});
}

function saveAcquisitionGroupConfigData(acqGroupSaveData,protocol,unitId){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveAcquisitionGroupHandsontableData',
		success:function(response) {
			rdata=Ext.JSON.decode(response.responseText);
			if (rdata.success) {
            	Ext.MessageBox.alert("信息","保存成功");
            	if(acqGroupSaveData.delidslist.length>0){//如果删除
            		Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(0);
            	}
            	Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert("信息","采控组数据保存失败");
            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
        	data: JSON.stringify(acqGroupSaveData),
        	protocol: protocol,
        	unitId: unitId
        }
	});
};
