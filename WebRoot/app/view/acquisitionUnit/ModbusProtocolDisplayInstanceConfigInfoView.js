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
                    id: 'ModbusProtocolDisplayInstanceProtocolSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolDisplayInstanceTreeSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                		var treePanel=Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id");
                		if(isNotVal(treePanel)){
                			treePanel.getStore().load();
                		}else{
                			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceProtocolTreeInfoStore');
                		}
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addAcqInstance,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolDisplayInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolDisplayInstanceConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolDisplayInstanceWindow");
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.importData,
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
        				Ext.getCmp("ImportDisplayInstanceWinTabLabel_Id").setHtml(loginUserLanguageResource.targetType+":【<font color=red>"+selectedDeviceTypeName+"</font>】,"+loginUserLanguageResourceFirstLower.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportDisplayInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportDisplayInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'20%',
                    layout: "fit",
                    id:'ModbusProtocolDisplayInstanceProtocolListPanel_Id',
                    border: false,
                    title:loginUserLanguageResource.protocolList,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left'
                },{
                	region: 'center',
                	border: false,
                	layout: "border",
                	items:[{
                		region: 'west',
                		width:'33%',
                		collapsible: true,
                        split: true,
                        border: false,
                        title:loginUserLanguageResource.displayInstanceList,
                    	layout: 'fit',
                    	id:"ModbusProtocolDisplayInstanceConfigPanel_Id"
                	},{
                    	region: 'center',
                    	title:loginUserLanguageResource.properties,
                    	border: false,
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
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateProtocolDisplayInstancePropertiesInfoTable(data){
	var root=[];
	var unitList=[];
	var protocolList=[];
	var protocolTreeGridPanelSelection= Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
	if(protocolTreeGridPanelSelection.length>0){
		if(protocolTreeGridPanelSelection[0].data.classes==1){
			protocolList.push(protocolTreeGridPanelSelection[0].data.code);
		}else{
			if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
				for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
					protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
				}
			}
		}
		
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getDisplayUnitList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			unitList=result.unitList;
			if(data.classes==0){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.rootNode;
				item1.value=loginUserLanguageResource.instanceList;
				root.push(item1);
			}else if(data.classes==1){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.instanceName;
				item1.value=data.text;
				root.push(item1);
				
				var item2={};
				item2.id=2;
				item2.title=loginUserLanguageResource.displayUnit;
				item2.value=data.protocol+'/'+data.displayUnitName;
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title=loginUserLanguageResource.sortNum;
				item3.value=data.sort;
				root.push(item3);
			}else if(data.classes==2){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.displayUnit;
				item1.value=data.text;
				root.push(item1);
			}
			
			if(protocolDisplayInstancePropertiesHandsontableHelper==null || protocolDisplayInstancePropertiesHandsontableHelper.hot==undefined){
				protocolDisplayInstancePropertiesHandsontableHelper = ProtocolDisplayInstancePropertiesHandsontableHelper.createNew("ProtocolDisplayInstancePropertiesTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolDisplayInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolDisplayInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolDisplayInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolDisplayInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolDisplayInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolDisplayInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolDisplayInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolDisplayInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocol: protocolList.join(",")
        }
	});
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
	        protocolDisplayInstancePropertiesHandsontableHelper.unitList=[];
	        
	        protocolDisplayInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
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
	        		colWidths: [1,4,5],
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
	                contextMenu: {
	                    items: {
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut
	                        }
	                    }
	                }, 
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
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    		this.type = 'dropdown';
		                    		this.source = protocolDisplayInstancePropertiesHandsontableHelper.unitList;
			                    	
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if (visualColIndex ==0 || visualColIndex ==1) {
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
	                	if(coords.col>=0 && coords.row>=0 && protocolDisplayInstancePropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
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
		var protocolCode='';
		var protocolTreeGridPanelSelection= Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
    	if(protocolTreeGridPanelSelection.length>0){
    		if(protocolTreeGridPanelSelection[0].data.classes==1){
    			protocolCode=protocolTreeGridPanelSelection[0].data.code;
    		}
    	}
		
		
		var selectedItem=Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		var propertiesData=protocolDisplayInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.name=propertiesData[0][2];
			saveData.displayUnitId=selectedItem.data.displayUnitId;
			saveData.displayUnitName=propertiesData[1][2];
			saveData.sort=propertiesData[2][2];
			SaveModbusProtocolDisplayInstanceData(saveData);
		}
	}
};

function SaveModbusProtocolDisplayInstanceData(saveData){
	var protocolList=[];
	var protocolTreeGridPanelSelection= Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
	if(protocolTreeGridPanelSelection.length>0){
		if(protocolTreeGridPanelSelection[0].data.classes==1){
			protocolList.push(protocolTreeGridPanelSelection[0].data.code);
		}else{
			if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
				for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
					protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
				}
			}
		}
	}
	var protocolCodes=protocolList.join(",");
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolDisplayInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").setValue(0);
					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.deleteSuccessfully);
				}else{
					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				}
				Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id").getStore().load();
            	
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			data: JSON.stringify(saveData),
			protocolCodes:protocolCodes
        }
	});
}