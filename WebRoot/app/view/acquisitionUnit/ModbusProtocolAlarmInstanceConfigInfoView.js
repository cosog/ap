//var protocolAlarmInstanceConfigNumItemsHandsontableHelper=null;
//var protocolAlarmInstanceConfigCalNumItemsHandsontableHelper=null;
//var protocolAlarmInstanceConfigSwitchItemsHandsontableHelper=null;
//var protocolAlarmInstanceConfigEnumItemsHandsontableHelper=null;
//var protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
//var protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper=null;
//var protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper=null;
//
//var protocolAlarmInstancePropertiesHandsontableHelper=null;

Ext.define('AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolAlarmInstanceConfigInfoView',
    layout: "fit",
    id:'modbusProtocolAlarmInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolAlarmInstanceTreeSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden: false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加实例',
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolAlarmInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAlarmInstanceConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: '导出',
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolAlarmInstanceWindow");
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
        				var window = Ext.create("AP.view.acquisitionUnit.ImportAlarmInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportAlarmInstanceWinTabLabel_Id").setHtml("实例将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,请确认<br/>&nbsp;");
//        			    Ext.getCmp("ImportAlarmInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportAlarmInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
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
                    	title:'报警实例列表',
                    	layout: 'fit',
                    	id:"ModbusProtocolAlarmInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ProtocolAlarmInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolAlarmInstancePropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolAlarmInstancePropertiesHandsontableHelper!=null && protocolAlarmInstancePropertiesHandsontableHelper.hot!=undefined){
//                            		protocolAlarmInstancePropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmInstancePropertiesHandsontableHelper.hot.updateSettings({
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
                    title:'报警项',
                    xtype: 'tabpanel',
                    id:"ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id",
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	title:'数据量',
                    	iconCls: 'check3',
                    	id:"ModbusProtocolAlarmInstanceNumItemsTableInfoPanel_Id",
                    	region: 'center',
                		layout: "border",
                		items: [{
                    		region: 'center',
                    		title:'采集项',
                    		layout: 'fit',
                    		id:'ModbusProtocolAlarmInstanceNumItemsConfigTableInfoPanel_id',
                            html:'<div class="ModbusProtocolAlarmInstanceNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceNumItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolAlarmInstanceConfigNumItemsHandsontableHelper!=null && protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
//                                		protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	},{
                    		region: 'south',
                        	height:'50%',
                        	title:'计算项',
                        	collapsible: true,
                            split: true,
                            layout: 'fit',
                            id:'ModbusProtocolAlarmInstanceCalNumItemsConfigTableInfoPanel_id',
                            html:'<div class="ModbusProtocolAlarmInstanceCalNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceCalNumItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper!=null && protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
//                                		protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	}]
                    },{
                    	title:'开关量',
                    	id:"ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="ModbusProtocolAlarmInstanceSwitchItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceSwitchItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper!=null && protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
//                            		protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    },{
                    	title:'枚举量',
                    	id:"ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="ModbusProtocolAlarmInstanceEnumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceEnumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper!=null && protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
//                            		protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    },{
                    	title:'工况诊断',
                    	id:"ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id",
                    	 layout: 'fit',
                         html:'<div class="ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceFESDiagramResultItemsConfigTableInfoDiv_id"></div></div>',
                         listeners: {
                             resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                             	if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null && protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
//                             		protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.refreshDimensions();
                             		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                             	}
                             }
                         }
                    },{
                    	title:'运行状态',
                    	id:"ModbusProtocolAlarmInstanceRunStatusItemsTableInfoPanel_Id",
                    	 layout: 'fit',
                         html:'<div class="ModbusProtocolAlarmInstanceRunStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceRunStatusItemsConfigTableInfoDiv_id"></div></div>',
                         listeners: {
                             resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                             	if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null && protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
//                             		protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.refreshDimensions();
                             		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                             	}
                             }
                         }
                    },{
                    	title:'通信状态',
                    	id:"ModbusProtocolAlarmInstanceCommStatusItemsTableInfoPanel_Id",
                    	 layout: 'fit',
                         html:'<div class="ModbusProtocolAlarmInstanceCommStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmInstanceCommStatusItemsConfigTableInfoDiv_id"></div></div>',
                         listeners: {
                             resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                             	if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null && protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
//                             		protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.refreshDimensions();
                             		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                             	}
                             }
                         }
                    }],
                    listeners: {
                    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				oldCard.setIconCls(null);
            				newCard.setIconCls('check3');
            			},
            			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    		var selectRow= Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").getValue();
                			var selectedItem=Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id").getStore().getAt(selectRow);
                    		if(newCard.id=="ModbusProtocolAlarmInstanceNumItemsTableInfoPanel_Id"){
                    			if(selectedItem.data.classes==0){
                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
                            			CreateProtocolAlarmInstanceNumItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
                            			CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes,selectedItem.data.children[0].deviceType);
                            		}else{
                            			CreateProtocolAlarmInstanceNumItemsConfigInfoTable(-1,'',1);
                            			CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(-1,'',1,selectedItem.data.deviceType);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceNumItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
                            		CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes,selectedItem.data.deviceType);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id"){
                        		if(selectedItem.data.classes==0){
                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
                            			CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id"){
                        		if(selectedItem.data.classes==0){
                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
                            			CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id"){
                        		if(selectedItem.data.classes==0){
                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
                            			CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmInstanceRunStatusItemsTableInfoPanel_Id"){
                        		if(selectedItem.data.classes==0){
                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
                            			CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmInstanceCommStatusItemsTableInfoPanel_Id"){
                        		if(selectedItem.data.classes==0){
                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
                            			CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
                            	}
                        	}
                    	}
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});
function CreateProtocolAlarmInstancePropertiesInfoTable(data){
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
		item2.title='报警单元';
		item2.value=data.alarmUnitName;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title='排序序号';
		item3.value=data.sort;
		root.push(item3);
	}else if(data.classes==2){
		var item1={};
		item1.id=1;
		item1.title='报警单元';
		item1.value=data.text;
		root.push(item1);
	}
	
	if(protocolAlarmInstancePropertiesHandsontableHelper==null || protocolAlarmInstancePropertiesHandsontableHelper.hot==undefined){
		protocolAlarmInstancePropertiesHandsontableHelper = ProtocolAlarmInstancePropertiesHandsontableHelper.createNew("ProtocolAlarmInstancePropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolAlarmInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolAlarmInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolAlarmInstancePropertiesHandsontableHelper.classes=data.classes;
		protocolAlarmInstancePropertiesHandsontableHelper.createTable(root);
	}else{
		protocolAlarmInstancePropertiesHandsontableHelper.classes=data.classes;
		protocolAlarmInstancePropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolAlarmInstancePropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstancePropertiesHandsontableHelper = {};
	        protocolAlarmInstancePropertiesHandsontableHelper.hot = '';
	        protocolAlarmInstancePropertiesHandsontableHelper.classes =null;
	        protocolAlarmInstancePropertiesHandsontableHelper.divid = divid;
	        protocolAlarmInstancePropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstancePropertiesHandsontableHelper.colHeaders=[];
	        protocolAlarmInstancePropertiesHandsontableHelper.columns=[];
	        protocolAlarmInstancePropertiesHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(251, 251, 251)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstancePropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstancePropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstancePropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstancePropertiesHandsontableHelper.divid);
	        	protocolAlarmInstancePropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,5,5],
	                columns:protocolAlarmInstancePropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstancePropertiesHandsontableHelper.colHeaders,//显示列头
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
		                    if(protocolAlarmInstancePropertiesHandsontableHelper.classes===0 || protocolAlarmInstancePropertiesHandsontableHelper.classes===2){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolAlarmInstancePropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolAlarmInstancePropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolAlarmInstancePropertiesHandsontableHelper);
			                    	}
			                    }else if(visualColIndex === 2 && visualRowIndex===2){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolAlarmInstancePropertiesHandsontableHelper);
			                    	}
			                    }
		                    	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolAlarmInstancePropertiesHandsontableHelper.addBoldBg;
				                }else{
				                	cellProperties.renderer = protocolAlarmInstancePropertiesHandsontableHelper.addCellStyle;
				                }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolAlarmInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstancePropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstancePropertiesHandsontableHelper!=null
	                		&& protocolAlarmInstancePropertiesHandsontableHelper.hot!=''
	                		&& protocolAlarmInstancePropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstancePropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolAlarmInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolAlarmInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolAlarmInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmInstancePropertiesHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceNumItemsConfigInfoTable(id,name,classes){
	Ext.getCmp("ModbusProtocolAlarmInstanceNumItemsConfigTableInfoPanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceNumItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/数据量报警项");
			
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigNumItemsHandsontableHelper==null || protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigNumItemsHandsontableHelper = ProtocolAlarmInstanceConfigNumItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceNumItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','上限','下限','回差','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
						+"{data:'upperLimit'},"
						+"{data:'lowerLimit'}," 
						+"{data:'hystersis'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:2
        }
	});
};

var ProtocolAlarmInstanceConfigNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigNumItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigNumItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(protocolAlarmInstanceConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigNumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigNumItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigNumItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(id,name,classes,deviceType){
	Ext.getCmp("ModbusProtocolAlarmInstanceCalNumItemsConfigTableInfoPanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceCalNumItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/数据量报警项");
			
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper==null || protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigCalNumItemsHandsontableHelper = ProtocolAlarmInstanceConfigCalNumItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceCalNumItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','上限','下限','回差','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'upperLimit'},"
						+"{data:'lowerLimit'}," 
						+"{data:'hystersis'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']},"
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:5,
			deviceType:deviceType
        }
	});
};

var ProtocolAlarmInstanceConfigCalNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigCalNumItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigCalNumItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigCalNumItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(id,name,classes){
	Ext.getCmp("ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceSwitchItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/开关量报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper==null || protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigSwitchItemsHandsontableHelper = ProtocolAlarmInstanceConfigSwitchItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceSwitchItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','位','含义','触发状态','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
						+"{data:'bitIndex'},"
						+"{data:'meaning'}," 
						+"{data:'value'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:0
        }
	});
};

var ProtocolAlarmInstanceConfigSwitchItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigSwitchItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigSwitchItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigSwitchItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(id,name,classes){
	Ext.getCmp("ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceEnumItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/枚举量报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper==null || protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigEnumItemsHandsontableHelper = ProtocolAlarmInstanceConfigEnumItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceEnumItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','值','含义','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
					 	+"{data:'value'}," 
						+"{data:'meaning'},"
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigEnumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigEnumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigEnumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigEnumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:1
        }
	});
};

var ProtocolAlarmInstanceConfigEnumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigEnumItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigEnumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigEnumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigEnumItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigEnumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigEnumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigEnumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigEnumItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigEnumItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(id,name,classes){
	Ext.getCmp("ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceFESDiagramResultItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/工况诊断报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper==null || protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper = ProtocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceFESDiagramResultItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:4
        }
	});
};

var ProtocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(id,name,classes){
	Ext.getCmp("ModbusProtocolAlarmInstanceRunStatusItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceRunStatusItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceRunStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/运行状态报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper==null || protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper = ProtocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceRunStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceRunStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:6
        }
	});
};

var ProtocolAlarmInstanceConfigRunStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(id,name,classes){
	Ext.getCmp("ModbusProtocolAlarmInstanceCommStatusItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAlarmInstanceCommStatusItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmInstanceCommStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/通信状态报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper==null || protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot==undefined){
				protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper = ProtocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.createNew("ModbusProtocolAlarmInstanceCommStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmInstanceCommStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			resolutionMode:3
        }
	});
};

var ProtocolAlarmInstanceConfigCommStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper = {};
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot1 = '';
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.divid = divid;
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns=[];
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.divid);
	        	protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.addCellStyle;
	    	            }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null
	                		&& protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper;
	    }
};

function SaveModbusProtocolAlarmInstanceConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolAlarmInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.name=propertiesData[0][2];
			saveData.alarmUnitId=selectedItem.data.alarmUnitId;
			saveData.sort=propertiesData[2][2];
			SaveModbusProtocolAlarmInstanceData(saveData);
		}
	}
};

function SaveModbusProtocolAlarmInstanceData(saveData){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolAlarmInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.MessageBox.alert("信息","保存成功");
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id").getStore().load();
            	
            } else {
            	Ext.MessageBox.alert("信息","采控单元数据保存失败");
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
