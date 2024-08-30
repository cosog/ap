//var protocolItemsConfigHandsontableHelper=null;
//var protocolPropertiesHandsontableHelper=null;
//var protocolItemsMeaningConfigHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolConfigInfoView',
    layout: "fit",
    id:'modbusProtocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
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
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加协议',
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addModbusProtocolAddrMappingConfigData();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAddrMappingConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: '存储字段表',
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.DatabaseColumnMappingWindow", {
                            title: '存储字段表'
                        });
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: '导出',
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolWindow");
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
        				if(selectedDeviceTypeId!=""){
        					var window = Ext.create("AP.view.acquisitionUnit.ImportProtocolWindow");
                            window.show();
        				}
        				Ext.getCmp("ImportProtocolWinTabLabel_Id").setHtml("协议将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,请确认<br/>&nbsp;");
        			    Ext.getCmp("ImportProtocolWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportProtocolWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                },'-', {
        			xtype: 'button',
        			text:'协议隶属迁移',
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'move',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ProtocoDeviceTypeChangeWindow", {
                            title: '协议隶属迁移'
                        });
                        window.show();
                        Ext.create("AP.store.acquisitionUnit.ProtocolDeviceTypeChangeProtocolListStore");
                        Ext.create("AP.store.acquisitionUnit.ProtocolDeviceTypeChangeDeviceTypeListStore");
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
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'协议配置',
                    	layout: 'fit',
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
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolPropertiesHandsontableHelper!=null && protocolPropertiesHandsontableHelper.hot!=undefined){
//                            		protocolPropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolPropertiesHandsontableHelper.hot.updateSettings({
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
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolItemsConfigHandsontableHelper!=null && protocolItemsConfigHandsontableHelper.hot!=undefined){
//                            		protocolItemsConfigHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolItemsConfigHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
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
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolItemsMeaningConfigHandsontableHelper!=null && protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
//                            		protocolItemsMeaningConfigHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolItemsMeaningConfigHandsontableHelper.hot.updateSettings({
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

function CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName,classes,code){
	Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigPanel_Id").setTitle(protocolName);
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolItemsConfigHandsontableHelper==null || protocolItemsConfigHandsontableHelper.hot==undefined){
				protocolItemsConfigHandsontableHelper = ProtocolItemsConfigHandsontableHelper.createNew("ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','',{label: '下位机', colspan: 5},{label: '上位机', colspan: 5}]," 
					+"['序号','名称','起始地址(十进制)','存储数据类型','存储数据数量','读写类型','响应模式','接口数据类型','小数位数','换算比例','单位','解析模式']" 
					+"]";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}},"
					 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
					 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}}," 
					 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '只写', '读写']}," 
					 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}}," 
						+"{data:'unit'}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}" 
						+"]";
				protocolItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolItemsConfigHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					protocolItemsConfigHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolItemsConfigHandsontableHelper.Data=result.totalRoot;
					protocolItemsConfigHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolItemsConfigHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					protocolItemsConfigHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolItemsConfigHandsontableHelper.Data=result.totalRoot;
					protocolItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
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
            		
            		var row1=protocolItemsConfigHandsontableHelper.hot.getDataAtRow(0);
            		var itemAddr=row1[2];
            		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,true);
        		}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolItemsConfigHandsontableHelper = {};
	        protocolItemsConfigHandsontableHelper.hot1 = '';
	        protocolItemsConfigHandsontableHelper.divid = divid;
	        protocolItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolItemsConfigHandsontableHelper.colHeaders=[];
	        protocolItemsConfigHandsontableHelper.columns=[];
	        protocolItemsConfigHandsontableHelper.AllData=[];
	        protocolItemsConfigHandsontableHelper.Data=[];
	        
	        protocolItemsConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolItemsConfigHandsontableHelper.divid);
	        	protocolItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,130,80,90,90,80,80,90,80,80,80,80],
	                columns:protocolItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolItemsConfigHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolItemsConfigHandsontableHelper.colHeaders,//显示列头
	                nestedRows:true,
	                columnHeaderHeight: 28,
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
//	                outsideClickDeselects:false,
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
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if (visualColIndex ==0) {
								cellProperties.readOnly = true;
			                }
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    if(protocolItemsConfigHandsontableHelper.columns[visualColIndex].type == undefined || protocolItemsConfigHandsontableHelper.columns[visualColIndex].type!='dropdown'){
                    		cellProperties.renderer = protocolItemsConfigHandsontableHelper.addCellStyle;
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
	                    		var row1=protocolItemsConfigHandsontableHelper.hot.getDataAtRow(row);
	                    		var itemAddr=row1[2];
	                    		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,true);
	                		}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolItemsConfigHandsontableHelper!=null&&protocolItemsConfigHandsontableHelper.hot!=''&&protocolItemsConfigHandsontableHelper.hot!=undefined && protocolItemsConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolItemsConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolItemsConfigHandsontableHelper.saveData = function () {}
	        protocolItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolItemsConfigHandsontableHelper;
	    }
};


function CreateProtocolConfigAddrMappingPropertiesInfoTable(data){
	var root=[];
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title='根节点';
		item1.value='协议列表';
		root.push(item1);
	}else if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title='协议名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='排序序号';
		item2.value=data.sort;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='协议隶属';
		item3.value=data.deviceTypeAllPath;
		root.push(item3);
	}
	
	if(protocolPropertiesHandsontableHelper==null || protocolPropertiesHandsontableHelper.hot==undefined){
		protocolPropertiesHandsontableHelper = ProtocolPropertiesHandsontableHelper.createNew("ModbusProtocolAddrMappingPropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'}," 
			+"{data:'title'}," 
			+"{data:'value'}]";
		protocolPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolPropertiesHandsontableHelper.classes=data.classes;
		protocolPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolPropertiesHandsontableHelper.classes=data.classes;
		protocolPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolPropertiesHandsontableHelper = {};
	        protocolPropertiesHandsontableHelper.hot = '';
	        protocolPropertiesHandsontableHelper.classes =null;
	        protocolPropertiesHandsontableHelper.divid = divid;
	        protocolPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolPropertiesHandsontableHelper.colHeaders=[];
	        protocolPropertiesHandsontableHelper.columns=[];
	        protocolPropertiesHandsontableHelper.AllData=[];
	        
	        protocolPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolPropertiesHandsontableHelper.divid);
	        	protocolPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,3,5],
	                columns:protocolPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolPropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    	if(protocolPropertiesHandsontableHelper.classes===0){
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolPropertiesHandsontableHelper.classes===1){
		                    	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
				                }else if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolPropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolPropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===2) {
			                    	cellProperties.readOnly = true;
			                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
			                    }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolPropertiesHandsontableHelper!=null&&protocolPropertiesHandsontableHelper.hot!=''&&protocolPropertiesHandsontableHelper.hot!=undefined && protocolPropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolPropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolPropertiesHandsontableHelper.saveData = function () {}
	        protocolPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolPropertiesHandsontableHelper;
	    }
};


function SaveModbusProtocolAddrMappingConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
	var AddrMappingItemsSelectRow= Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!='' && protocolPropertiesHandsontableHelper!=null && protocolPropertiesHandsontableHelper.hot!=null){
		var selectedItem=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var protocolConfigData={};
		var propertiesData=protocolPropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是协议
			protocolConfigData=selectedItem.data;
			protocolConfigData.text=propertiesData[0][2];
//			protocolConfigData.deviceType=(propertiesData[1][2]=="抽油机井"?0:1);
			protocolConfigData.sort=propertiesData[1][2];
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
			
			if(protocolItemsConfigHandsontableHelper!=null && protocolItemsConfigHandsontableHelper.hot!=null){
				var driverConfigItemsData=protocolItemsConfigHandsontableHelper.hot.getData();
				for(var i=0;i<driverConfigItemsData.length;i++){
					if(isNotVal(driverConfigItemsData[i][1])){
						var item={};
						
						item.Title=driverConfigItemsData[i][1];
						item.Addr=parseInt(driverConfigItemsData[i][2]);
						item.StoreDataType=driverConfigItemsData[i][3];
						item.Quantity=parseInt(driverConfigItemsData[i][4]);
						item.RWType=driverConfigItemsData[i][5];
						item.AcqMode=driverConfigItemsData[i][6];
						item.IFDataType=driverConfigItemsData[i][7];
						item.Prec=(item.IFDataType!=null && item.IFDataType.toLowerCase().indexOf('float')>=0)?(driverConfigItemsData[i][8]==''?0:driverConfigItemsData[i][8]):0;
						item.Ratio=parseFloat(driverConfigItemsData[i][9]);
						
//						if(item.IFDataType.toLowerCase().indexOf('float')>=0 && item.Ratio==0.1){
//							item.Prec=1;
//						}else if(item.IFDataType.toLowerCase().indexOf('float')>=0 && item.Ratio==0.01){
//							item.Prec=2;
//						}else if(item.IFDataType.toLowerCase().indexOf('float')>=0 && item.Ratio==0.001){
//							item.Prec=3;
//						}
						
						
						item.Unit=driverConfigItemsData[i][10];
						item.ResolutionMode=driverConfigItemsData[i][11];
						if(i==AddrMappingItemsSelectRow){
							item.Meaning=[];
							var itemsMeaningData=protocolItemsMeaningConfigHandsontableHelper.hot.getData();
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
			protocolItemsConfigHandsontableHelper.clearContainer();
			if (data.success) {
				Ext.getCmp("modbusProtocolConfigInfoViewId").getEl().unmask();
				Ext.MessageBox.alert("信息","保存成功");
				
				if(configInfo.delidslist!=undefined && configInfo.delidslist.length>0){//如果删除
					Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").setValue(0);
            		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(0);
				}
				
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
	if(isNew&&protocolItemsMeaningConfigHandsontableHelper!=null){
		if(protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
			protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
		}
		protocolItemsMeaningConfigHandsontableHelper=null;
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
			if(protocolItemsMeaningConfigHandsontableHelper==null || protocolItemsMeaningConfigHandsontableHelper.hot==undefined){
				protocolItemsMeaningConfigHandsontableHelper = ProtocolItemsMeaningConfigHandsontableHelper.createNew("ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id");
				var colHeaders="['数值','含义']";
				if(result.itemResolutionMode==0){
					colHeaders="['位','含义']";
				}
				var columns="[{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolItemsMeaningConfigHandsontableHelper);}},"
						+"{data:'meaning'}" 
						+"]";
				
				protocolItemsMeaningConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolItemsMeaningConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolItemsMeaningConfigHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolItemsMeaningConfigHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolItemsMeaningConfigHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolItemsMeaningConfigHandsontableHelper.hot.loadData(result.totalRoot);
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

var ProtocolItemsMeaningConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolItemsMeaningConfigHandsontableHelper = {};
	        protocolItemsMeaningConfigHandsontableHelper.hot1 = '';
	        protocolItemsMeaningConfigHandsontableHelper.divid = divid;
	        protocolItemsMeaningConfigHandsontableHelper.validresult=true;//数据校验
	        protocolItemsMeaningConfigHandsontableHelper.colHeaders=[];
	        protocolItemsMeaningConfigHandsontableHelper.columns=[];
	        protocolItemsMeaningConfigHandsontableHelper.AllData=[];
	        
	        protocolItemsMeaningConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolItemsMeaningConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolItemsMeaningConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolItemsMeaningConfigHandsontableHelper.divid);
	        	protocolItemsMeaningConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [1,3],
	                columns:protocolItemsMeaningConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolItemsMeaningConfigHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag!=1){
	                    	cellProperties.readOnly = true;
	                    }
	                    cellProperties.renderer = protocolItemsMeaningConfigHandsontableHelper.addCellStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolItemsMeaningConfigHandsontableHelper!=null&&protocolItemsMeaningConfigHandsontableHelper.hot!=''&&protocolItemsMeaningConfigHandsontableHelper.hot!=undefined && protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolItemsMeaningConfigHandsontableHelper.saveData = function () {}
	        protocolItemsMeaningConfigHandsontableHelper.clearContainer = function () {
	        	protocolItemsMeaningConfigHandsontableHelper.AllData = [];
	        }
	        return protocolItemsMeaningConfigHandsontableHelper;
	    }
};
