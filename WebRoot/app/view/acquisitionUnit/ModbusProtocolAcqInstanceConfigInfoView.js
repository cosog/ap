var protocolInstanceConfigItemsHandsontableHelper=null;
var protocolConfigInstancePropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolAcqInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolAcqInstanceConfigInfoView',
    layout: "fit",
    id:'ModbusProtocolAcqInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ScadaProtocolModbusInstanceConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },'->',{
        			xtype: 'button',
                    text: '添加实例',
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolInstanceConfigTreeData();
        			}
                }],
                layout: "border",
                items: [{
                	border: true,
                	region: 'west',
                	width:'28%',
                    layout: "border",
                    border: true,
                    header: false,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'采控实例列表',
//                    	autoScroll:true,
                        scrollable: true,
                    	id:"ModbusProtocolInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ProtocolConfigInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolConfigInstancePropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	if(protocolConfigInstancePropertiesHandsontableHelper!=null && protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined){
                            		protocolConfigInstancePropertiesHandsontableHelper.hot.refreshDimensions();
                            	}
                            }
                        }
                    }]
                },{
                	border: true,
                	region: 'center',
                	title:'采控项',
                	id:"ModbusProtocolInstanceItemsTableTabPanel_Id",
                	layout: 'fit',
                    html:'<div class="ModbusProtocolInstanceItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolInstanceItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if(protocolInstanceConfigItemsHandsontableHelper!=null && protocolInstanceConfigItemsHandsontableHelper.hot!=undefined){
                        		protocolInstanceConfigItemsHandsontableHelper.hot.refreshDimensions();
                        	}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});
function CreateProtocolInstanceConfigPropertiesInfoTable(data){
	var root=[];
	var rpcAcqUnit=[];
	var pcpAcqUnit=[];
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getAcqUnitList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			rpcAcqUnit=result.rpcAcqUnit;
			pcpAcqUnit=result.pcpAcqUnit;
			
			if(data.classes==1){
				var item1={};
				item1.id=1;
				item1.title='实例名称';
				item1.value=data.text;
				root.push(item1);
				
				var item2={};
				item2.id=2;
				item2.title='设备类型';
				item2.value=(data.deviceType==0?"抽油机":"螺杆泵");
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title='采控单元';
				item3.value=data.unitName;
				root.push(item3);
				
				var item4={};
				item4.id=4;
				item4.title='采集协议类型';
				item4.value=data.acqProtocolType;
				root.push(item4);
				
				var item5={};
				item5.id=5;
				item5.title='控制协议类型';
				item5.value=data.ctrlProtocolType;
				root.push(item5);
				
				
				var item6={};
				item6.id=6;
				item6.title='前后缀十六进制';
				if(parseInt(data.prefixSuffixHex)==1){
					item6.value=true;
				}else{
					item6.value=false;
				}
				root.push(item6);
				
				var item7={};
				item7.id=7;
				item7.title='注册包前缀(HEX/ASC)';
				item7.value=data.signInPrefix;
				root.push(item7);
				
				var item8={};
				item8.id=8;
				item8.title='注册包后缀(HEX/ASC)';
				item8.value=data.signInSuffix;
				root.push(item8);
				
				var item9={};
				item9.id=9;
				item9.title='心跳包前缀(HEX/ASC)';
				item9.value=data.heartbeatPrefix;
				root.push(item9);
				
				var item10={};
				item10.id=10;
				item10.title='心跳包后缀(HEX/ASC)';
				item10.value=data.heartbeatSuffix;
				root.push(item10);
				
				var item11={};
				item11.id=11;
				item11.title='单包发送间隔(ms)';
				item11.value=data.packetSendInterval;
				root.push(item11);
				
				var item12={};
				item12.id=12;
				item12.title='排序序号';
				item12.value=data.sort;
				root.push(item12);
			}
			
			if(protocolConfigInstancePropertiesHandsontableHelper==null || protocolConfigInstancePropertiesHandsontableHelper.hot==undefined){
				protocolConfigInstancePropertiesHandsontableHelper = ProtocolConfigInstancePropertiesHandsontableHelper.createNew("ProtocolConfigInstancePropertiesTableInfoDiv_id");
				var colHeaders="['序号','名称','变量']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolConfigInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolConfigInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolConfigInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolConfigInstancePropertiesHandsontableHelper.rpcAcqUnit=rpcAcqUnit;
		        protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit=pcpAcqUnit;
				protocolConfigInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolConfigInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolConfigInstancePropertiesHandsontableHelper.rpcAcqUnit=rpcAcqUnit;
		        protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit=pcpAcqUnit;
				protocolConfigInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			
        }
	});
};

var ProtocolConfigInstancePropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigInstancePropertiesHandsontableHelper = {};
	        protocolConfigInstancePropertiesHandsontableHelper.hot = '';
	        protocolConfigInstancePropertiesHandsontableHelper.classes =null;
	        protocolConfigInstancePropertiesHandsontableHelper.divid = divid;
	        protocolConfigInstancePropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolConfigInstancePropertiesHandsontableHelper.colHeaders=[];
	        protocolConfigInstancePropertiesHandsontableHelper.columns=[];
	        protocolConfigInstancePropertiesHandsontableHelper.AllData=[];
	        protocolConfigInstancePropertiesHandsontableHelper.rpcAcqUnit=[];
	        protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit=[];
	        
	        protocolConfigInstancePropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
//	        protocolConfigInstancePropertiesHandsontableHelper.test = function (instance, td, row, col, prop, value, cellProperties) {
//	            Handsontable.renderers.TextRenderer.apply(this, arguments);
//	            cellProperties.type = 'dropdown';
//	            cellProperties.source = ['抽油机','螺杆泵'];
//	            cellProperties.strict = true;
//	            cellProperties.allowInvalid = false;
//	        }
	        
	        protocolConfigInstancePropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigInstancePropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigInstancePropertiesHandsontableHelper.divid);
	        	protocolConfigInstancePropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,5,5],
	                columns:protocolConfigInstancePropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigInstancePropertiesHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    if(protocolConfigInstancePropertiesHandsontableHelper.classes===1){
	                    	if (visualColIndex === 2 && visualRowIndex===1) {
		                    	this.type = 'dropdown';
		                    	this.source = ['抽油机','螺杆泵'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    	if (visualColIndex === 2 && visualRowIndex===2) {
		                    	var deviceType='';
		                    	if(isNotVal(protocolConfigInstancePropertiesHandsontableHelper.hot)){
		                    		deviceType=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(1,2);
		                    	}
		                    	
	                    		this.type = 'dropdown';
	                    		if(deviceType==='抽油机'){
	                    			this.source = protocolConfigInstancePropertiesHandsontableHelper.rpcAcqUnit;
	                    		}else{
	                    			this.source = protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit;
	                    		}
		                    	
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    	if (visualColIndex === 2 && visualRowIndex===3) {
		                    	this.type = 'dropdown';
		                    	this.source = ['modbus-tcp','modbus-rtu','private-kd93','private-lq1000'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    	if (visualColIndex === 2 && visualRowIndex===4) {
		                    	this.type = 'dropdown';
		                    	this.source = ['modbus-tcp','modbus-rtu'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
	                    	
	                    	if (visualColIndex === 2 && visualRowIndex===5) {
		                    	this.type = 'checkbox';
//		                    	this.label={
//			                            position: 'before',
//			                            value: '16进制? '
//			                          };
		                    }
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        protocolConfigInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigInstancePropertiesHandsontableHelper;
	    }
};

function CreateProtocolInstanceAcqItemsInfoTable(id,instanceName,classes){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolInstanceItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolInstanceItemsTableTabPanel_Id").setTitle(instanceName+"/采控项");
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolInstanceConfigItemsHandsontableHelper==null || protocolInstanceConfigItemsHandsontableHelper.hot==undefined){
				protocolInstanceConfigItemsHandsontableHelper = ProtocolInstanceConfigItemsHandsontableHelper.createNew("ModbusProtocolInstanceItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','数量','存储数据类型','接口数据类型','读写类型','单位','换算比例','采集模式']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}},"
						+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}}," 
						+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '读写']}," 
						+"{data:'unit'}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}}," 
						+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}" 
						+"]";
				protocolInstanceConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolInstanceConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolInstanceConfigItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolInstanceConfigItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolInstanceConfigItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolInstanceConfigItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes
        }
	});
};

var ProtocolInstanceConfigItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolInstanceConfigItemsHandsontableHelper = {};
	        protocolInstanceConfigItemsHandsontableHelper.hot1 = '';
	        protocolInstanceConfigItemsHandsontableHelper.divid = divid;
	        protocolInstanceConfigItemsHandsontableHelper.validresult=true;//数据校验
	        protocolInstanceConfigItemsHandsontableHelper.colHeaders=[];
	        protocolInstanceConfigItemsHandsontableHelper.columns=[];
	        protocolInstanceConfigItemsHandsontableHelper.AllData=[];
	        
	        protocolInstanceConfigItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolInstanceConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolInstanceConfigItemsHandsontableHelper.divid);
	        	protocolInstanceConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80],
	                columns:protocolInstanceConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
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
	        return protocolInstanceConfigItemsHandsontableHelper;
	    }
};

function SaveModbusProtocolInstanceConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolConfigInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.name=propertiesData[0][2];
			saveData.deviceType=(propertiesData[1][2]=="抽油机"?0:1);
			saveData.unitId=selectedItem.data.unitId;
			saveData.unitName=propertiesData[2][2];
			saveData.acqProtocolType=propertiesData[3][2];
			saveData.ctrlProtocolType=propertiesData[4][2];
			
			if(propertiesData[5][2]==true){
				saveData.prefixSuffixHex=1;
			}else{
				saveData.prefixSuffixHex=0;
			}
			
			saveData.signInPrefix=propertiesData[6][2];
			saveData.signInSuffix=propertiesData[7][2];
			
			saveData.heartbeatPrefix=propertiesData[8][2];
			saveData.heartbeatSuffix=propertiesData[9][2];
			
			saveData.packetSendInterval=propertiesData[10][2];
			
			saveData.sort=propertiesData[11][2];
			
			SaveModbusProtocolAcqInstanceData(saveData);
		}
	}
};

function SaveModbusProtocolAcqInstanceData(saveData){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id").getStore().load();
            	Ext.MessageBox.alert("信息","保存成功");
            } else {
            	Ext.MessageBox.alert("信息","保存失败");
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

