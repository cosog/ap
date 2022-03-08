var protocolConfigAddrMappingItemsHandsontableHelper=null;
var protocolConfigAddrMaooingPropertiesHandsontableHelper=null;
var protocolAddrMappingItemsMeaningConfigHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolConfigInfoView',
    layout: "fit",
    id:'modbusProtocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolAddrMappingConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolAddrMappingItemsSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },'->',{
        			xtype: 'button',
                    text: '添加协议',
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addModbusProtocolAddrMappingConfigData();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAddrMappingConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: '存储字段表',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.DatabaseColumnMappingWindow", {
                            title: '存储字段表'
                        });
                        window.show();
//                        Ext.create("AP.store.acquisitionUnit.DatabaseColumnMappingStore");
        			}
                }],
                layout: "border",
                items: [{
                	border: true,
                	region: 'west',
                	width:'15%',
                    layout: "border",
                    border: true,
                    header: false,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'协议配置',
//                    	autoScroll:true,
                        scrollable: true,
                    	id:"ModbusProtocolAddrMappingConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolAddrMappingPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAddrMappingPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	if(protocolConfigAddrMaooingPropertiesHandsontableHelper!=null && protocolConfigAddrMaooingPropertiesHandsontableHelper.hot!=undefined){
                            		protocolConfigAddrMaooingPropertiesHandsontableHelper.hot.refreshDimensions();
                            	}
                            }
                        }
                    }]
                },{
                	border: true,
//                    flex: 4,
                	region: 'center',
                    title:'采控项配置',
                    id:"ModbusProtocolAddrMappingItemsConfigPanel_Id",
                    layout: "border",
                    border: true,
                    items: [{
                    	region: 'center',
                    	title:'采控项',
                    	layout: 'fit',
                    	header:false,
                    	id:'ModbusProtocolAddrMappingItemsConfigTabPanel_Id',
                        html:'<div class="ModbusProtocolAddrMappingItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	if(protocolConfigAddrMappingItemsHandsontableHelper!=null && protocolConfigAddrMappingItemsHandsontableHelper.hot!=undefined){
                            		protocolConfigAddrMappingItemsHandsontableHelper.hot.refreshDimensions();
                            	}
                            }
                        }
                    },{
                    	region: 'east',
                    	width:'15%',
                    	title:'含义',
                    	id:'ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id',
                    	header:false,
                    	collapsible: true,
                    	collapsed: false,
                        split: true,
                        layout: 'fit',
                        html:'<div class="ModbusProtocolAddrMappingItemsMeaningTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	if(protocolAddrMappingItemsMeaningConfigHandsontableHelper!=null && protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot!=undefined){
                            		protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot.refreshDimensions();
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

function CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName,classes,code){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigPanel_Id").setTitle(protocolName);
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolConfigAddrMappingItemsHandsontableHelper==null || protocolConfigAddrMappingItemsHandsontableHelper.hot==undefined){
				protocolConfigAddrMappingItemsHandsontableHelper = ProtocolConfigAddrMappingItemsHandsontableHelper.createNew("ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','数量','存储数据类型','接口数据类型','读写类型','单位','换算比例','解析模式','采集模式']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}},"
						+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
						+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['byte','int16','uint16','float32','bcd']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '只写', '读写']}," 
						+"{data:'unit'}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}," 
						+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}" 
						+"]";
				protocolConfigAddrMappingItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolConfigAddrMappingItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolConfigAddrMappingItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolConfigAddrMappingItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolConfigAddrMappingItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolConfigAddrMappingItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
			if(result.totalRoot.length==0){
				Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue('');
				CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable('','',true);
			}else{
				Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(0);
				var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
				var protocolCode="";
        		if(ScadaDriverModbusConfigSelectRow!=''){
        			var selectedProtocol=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
            		if(selectedProtocol.data.classes==1){//选中的是协议
            			protocolCode=selectedProtocol.data.code;
            		}else if(selectedProtocol.data.classes==0 && isNotVal(selectedProtocol.data.children) && selectedProtocol.data.children.length>0){
            			protocolCode=selectedProtocol.data.children[0].code;
            		}
            		
            		var row1=protocolConfigAddrMappingItemsHandsontableHelper.hot.getDataAtRow(0);
            		var itemAddr=row1[2];
            		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,true);
        		}
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolConfigAddrMappingItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigAddrMappingItemsHandsontableHelper = {};
	        protocolConfigAddrMappingItemsHandsontableHelper.hot1 = '';
	        protocolConfigAddrMappingItemsHandsontableHelper.divid = divid;
	        protocolConfigAddrMappingItemsHandsontableHelper.validresult=true;//数据校验
	        protocolConfigAddrMappingItemsHandsontableHelper.colHeaders=[];
	        protocolConfigAddrMappingItemsHandsontableHelper.columns=[];
	        protocolConfigAddrMappingItemsHandsontableHelper.AllData=[];
	        
	        protocolConfigAddrMappingItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigAddrMappingItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        protocolConfigAddrMappingItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigAddrMappingItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigAddrMappingItemsHandsontableHelper.divid);
	        	protocolConfigAddrMappingItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,130,80,80,80,80,80,80,80,80,80],
	                columns:protocolConfigAddrMappingItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigAddrMappingItemsHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                	items: {
	                	    "row_above": {
	                	      name: '向上插入一行',
	                	    },
	                	    "row_below": {
	                	      name: '向下插入一行',
	                	    },
	                	    "col_left": {
	                	      name: '向左插入一列',
	                	    },
	                	    "col_right": {
	                	      name: '向右插入一列',
	                	    },
	                	    "remove_row": {
	                	      name: '删除行',
	                	    },
	                	    "remove_col": {
	                	      name: '删除列',
	                	    },
	                	    "merge_cell": {
	                	      name: '合并单元格',
	                	    },
	                	    "copy": {
	                	      name: '复制',
	                	    },
	                	    "cut": {
	                	      name: '剪切',
	                	    },
	                	    "paste": {
	                	      name: '粘贴',
	                	      disabled: function() {
	                	      },
	                	      callback: function() {
	                	      }
	                	    }
	                	}
	                },//右键菜单展示
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex ==0) {
							cellProperties.readOnly = true;
//							cellProperties.renderer = protocolConfigAddrMappingItemsHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row, column, row2, column2, selectionLayerLevel) {
	                	if(row==row2 && column==column2){
	                		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(row);
	                		var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
	                		var protocolCode="";
	                		if(ScadaDriverModbusConfigSelectRow!=''){
	                			var selectedProtocol=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
	                    		if(selectedProtocol.data.classes==1){//选中的是协议
	                    			protocolCode=selectedProtocol.data.code;
	                    		}else if(selectedProtocol.data.classes==0 && isNotVal(selectedProtocol.data.children) && selectedProtocol.data.children.length>0){
	                    			protocolCode=selectedProtocol.data.children[0].code;
	                    		}
	                    		var row1=protocolConfigAddrMappingItemsHandsontableHelper.hot.getDataAtRow(row);
	                    		var itemAddr=row1[2];
	                    		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,true);
	                		}
	                	}
	                },
//	                afterOnCellMouseDown : function (event, coords, TD){
//	                	alert(coords);
//	                },
//	        		,colHeaders: function (col) { 
//	                    switch (col) { 
//	                     case 0: 
//	                      var txt = "<input type='checkbox' class='checker' "; 
//	                      txt += isChecked(data) ? 'checked="checked"' : ''; 
//	                      txt += "> 全选"; 
//	                      return txt; 
//	                     default:
//	                    	 return protocolConfigAddrMappingItemsHandsontableHelper.colHeaders[col]; 
//	                    } 
//	                 }
	        	});
	        }
	        //保存数据
	        protocolConfigAddrMappingItemsHandsontableHelper.saveData = function () {}
	        protocolConfigAddrMappingItemsHandsontableHelper.clearContainer = function () {
	        	protocolConfigAddrMappingItemsHandsontableHelper.AllData = [];
	        }
	        return protocolConfigAddrMappingItemsHandsontableHelper;
	    }
};


function CreateProtocolConfigAddrMappingPropertiesInfoTable(data){
	var root=[];
	if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title='协议名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='设备类型';
		item2.value=(data.deviceType==0?"抽油机":"螺杆泵");
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='排序序号';
		item3.value=data.sort;
		root.push(item3);
	}
	
	if(protocolConfigAddrMaooingPropertiesHandsontableHelper==null || protocolConfigAddrMaooingPropertiesHandsontableHelper.hot==undefined){
		protocolConfigAddrMaooingPropertiesHandsontableHelper = ProtocolConfigAddrMaooingPropertiesHandsontableHelper.createNew("ModbusProtocolAddrMappingPropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolConfigAddrMaooingPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolConfigAddrMaooingPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolConfigAddrMaooingPropertiesHandsontableHelper.classes=data.classes;
		protocolConfigAddrMaooingPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolConfigAddrMaooingPropertiesHandsontableHelper.classes=data.classes;
		protocolConfigAddrMaooingPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolConfigAddrMaooingPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigAddrMaooingPropertiesHandsontableHelper = {};
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.hot = '';
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.classes =null;
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.divid = divid;
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.colHeaders=[];
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.columns=[];
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.AllData=[];
	        
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigAddrMaooingPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigAddrMaooingPropertiesHandsontableHelper.divid);
	        	protocolConfigAddrMaooingPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,3,5],
	                columns:protocolConfigAddrMaooingPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigAddrMaooingPropertiesHandsontableHelper.colHeaders,//显示列头
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
							cellProperties.renderer = protocolConfigAddrMaooingPropertiesHandsontableHelper.addBoldBg;
		                }
	                    if(protocolConfigAddrMaooingPropertiesHandsontableHelper.classes===1){
	                    	if (visualColIndex === 2 && visualRowIndex===1) {
		                    	this.type = 'dropdown';
		                    	this.source = ['抽油机','螺杆泵'];
		                    	this.strict = true;
		                    	this.allowInvalid = false;
		                    }
//	                    	if (visualColIndex === 2 && visualRowIndex===2) {
//		                    	this.type = 'dropdown';
//		                    	this.source = ['modbus-tcp','modbus-rtu'];
//		                    	this.strict = true;
//		                    	this.allowInvalid = false;
//		                    }
	                    }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigAddrMaooingPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigAddrMaooingPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigAddrMaooingPropertiesHandsontableHelper;
	    }
};


function SaveModbusProtocolAddrMappingConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
	var AddrMappingItemsSelectRow= Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!='' && protocolConfigAddrMaooingPropertiesHandsontableHelper!=null && protocolConfigAddrMaooingPropertiesHandsontableHelper.hot!=null){
		var selectedItem=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var protocolConfigData={};
		var propertiesData=protocolConfigAddrMaooingPropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是协议
			protocolConfigData=selectedItem.data;
			protocolConfigData.text=propertiesData[0][2];
			protocolConfigData.deviceType=(propertiesData[1][2]=="抽油机"?0:1);
			protocolConfigData.sort=propertiesData[2][2];
		}else if(selectedItem.data.classes==0 && isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
			protocolConfigData=selectedItem.data.children[0];
		}
		if(isNotVal(protocolConfigData.text)){
			var configInfo={};
			configInfo.ProtocolName=protocolConfigData.text;
			configInfo.ProtocolCode=protocolConfigData.code;
			configInfo.DeviceType=protocolConfigData.deviceType;
			configInfo.Sort=protocolConfigData.sort;
			configInfo.DataConfig=[];
			
			if(protocolConfigAddrMappingItemsHandsontableHelper!=null && protocolConfigAddrMappingItemsHandsontableHelper.hot!=null){
				var driverConfigItemsData=protocolConfigAddrMappingItemsHandsontableHelper.hot.getData();
				for(var i=0;i<driverConfigItemsData.length;i++){
					if(isNotVal(driverConfigItemsData[i][1])){
						var item={};
						item.Title=driverConfigItemsData[i][1];
						item.Addr=parseInt(driverConfigItemsData[i][2]);
						item.Quantity=parseInt(driverConfigItemsData[i][3]);
						item.StoreDataType=driverConfigItemsData[i][4];
						item.IFDataType=driverConfigItemsData[i][5];
						item.RWType=driverConfigItemsData[i][6];
						item.Unit=driverConfigItemsData[i][7];
						item.Ratio=parseFloat(driverConfigItemsData[i][8]);
						item.ResolutionMode=driverConfigItemsData[i][9];
						item.AcqMode=driverConfigItemsData[i][10];
						if(i==AddrMappingItemsSelectRow){
							item.Meaning=[];
							var itemsMeaningData=protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot.getData();
							for(var j=0;j<itemsMeaningData.length;j++){
								if(isNotVal(itemsMeaningData[j][0]) && isNotVal(itemsMeaningData[j][1])){
									var meaning={};
									meaning.Value=itemsMeaningData[j][0];
									meaning.Meaning=itemsMeaningData[j][1];
									item.Meaning.push(meaning);
								}
							}
						}
						configInfo.DataConfig.push(item);
					}
				}
			}
			saveModbusProtocolAddrMappingConfigData(configInfo);
		}else{
			Ext.MessageBox.alert("提示","协议名称不能为空！");
		}
	}
};

function saveModbusProtocolAddrMappingConfigData(configInfo){
	Ext.getCmp("modbusProtocolConfigInfoViewId").el.mask("协议保存中...").show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveModbusProtocolAddrMappingConfigData',
		success:function(response) {
			var data=Ext.JSON.decode(response.responseText);
			protocolConfigAddrMappingItemsHandsontableHelper.clearContainer();
			if (data.success) {
				Ext.getCmp("modbusProtocolConfigInfoViewId").getEl().unmask();
				Ext.MessageBox.alert("信息","保存成功");
            	Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
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

function CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,isNew){
	if(isNew&&protocolAddrMappingItemsMeaningConfigHandsontableHelper!=null){
		if(protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot!=undefined){
			protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot.destroy();
		}
		protocolAddrMappingItemsMeaningConfigHandsontableHelper=null;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolItemMeaningConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id").setTitle(result.itemTitle+"含义");
			if(result.itemResolutionMode==2){
				Ext.getCmp("ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id").setTitle("");
			}
			if(protocolAddrMappingItemsMeaningConfigHandsontableHelper==null || protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot==undefined){
				protocolAddrMappingItemsMeaningConfigHandsontableHelper = ProtocolAddrMappingItemsMeaningConfigHandsontableHelper.createNew("ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id");
				var colHeaders="['值','含义']";
				if(result.itemResolutionMode==0){
					colHeaders="['位','含义']";
				}
				var columns="[{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAddrMappingItemsMeaningConfigHandsontableHelper);}},"
						+"{data:'meaning'}" 
						+"]";
				
				protocolAddrMappingItemsMeaningConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAddrMappingItemsMeaningConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAddrMappingItemsMeaningConfigHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAddrMappingItemsMeaningConfigHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolCode:protocolCode,
			itemAddr:itemAddr
        }
	});
};

var ProtocolAddrMappingItemsMeaningConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAddrMappingItemsMeaningConfigHandsontableHelper = {};
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot1 = '';
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.divid = divid;
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.validresult=true;//数据校验
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.colHeaders=[];
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.columns=[];
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.AllData=[];
	        
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(184, 184, 184)';
	        }
	        
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAddrMappingItemsMeaningConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAddrMappingItemsMeaningConfigHandsontableHelper.divid);
	        	protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [1,3],
	                columns:protocolAddrMappingItemsMeaningConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAddrMappingItemsMeaningConfigHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                	items: {
	                	    "row_above": {
	                	      name: '向上插入一行',
	                	    },
	                	    "row_below": {
	                	      name: '向下插入一行',
	                	    },
	                	    "col_left": {
	                	      name: '向左插入一列',
	                	    },
	                	    "col_right": {
	                	      name: '向右插入一列',
	                	    },
	                	    "remove_row": {
	                	      name: '删除行',
	                	    },
	                	    "remove_col": {
	                	      name: '删除列',
	                	    },
	                	    "merge_cell": {
	                	      name: '合并单元格',
	                	    },
	                	    "copy": {
	                	      name: '复制',
	                	    },
	                	    "cut": {
	                	      name: '剪切',
	                	    },
	                	    "paste": {
	                	      name: '粘贴',
	                	      disabled: function() {
	                	      },
	                	      callback: function() {
	                	      }
	                	    }
	                	}
	                },//右键菜单展示
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
//	                    if (visualColIndex ==1 || visualColIndex ==2) {
//							cellProperties.readOnly = true;
//							cellProperties.renderer = protocolAddrMappingItemsMeaningConfigHandsontableHelper.addBoldBg;
//		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        //保存数据
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.saveData = function () {}
	        protocolAddrMappingItemsMeaningConfigHandsontableHelper.clearContainer = function () {
	        	protocolAddrMappingItemsMeaningConfigHandsontableHelper.AllData = [];
	        }
	        return protocolAddrMappingItemsMeaningConfigHandsontableHelper;
	    }
};
