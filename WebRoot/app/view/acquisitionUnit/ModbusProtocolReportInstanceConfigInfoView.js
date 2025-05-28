//var protocolReportInstancePropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolReportInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportInstanceConfigInfoView',
    layout: "fit",
    id:'modbusProtocolReportInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolReportInstanceTreeSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addAcqInstance,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolReportInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveReportInstanceData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolReportInstanceWindow");
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
        				var window = Ext.create("AP.view.acquisitionUnit.ImportReportInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportReportInstanceWinTabLabel_Id").setHtml("实例将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportReportInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportReportInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'33%',
                	title:loginUserLanguageResource.reportInstanceList,
                	layout: 'fit',
                	border: false,
                	collapsible: true,
                    split: true,
                	id:"ModbusProtocolReportInstanceConfigPanel_Id"
                },{
                	region: 'center',
                	title:loginUserLanguageResource.properties,
                	border: false,
                	layout: 'fit',
                    html:'<div class="ModbusProtocolReportInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolReportInstancePropertiesTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if (protocolReportInstancePropertiesHandsontableHelper != null && protocolReportInstancePropertiesHandsontableHelper.hot != null && protocolReportInstancePropertiesHandsontableHelper.hot != undefined) {
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		protocolReportInstancePropertiesHandsontableHelper.hot.updateSettings({
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

function SaveReportInstanceData(){
	var InstanceTreeSelectRow= Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").getValue();
	if(InstanceTreeSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getStore().getAt(InstanceTreeSelectRow);
		var propertiesData=protocolReportInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.name=propertiesData[0][2];
			saveData.unitName=propertiesData[1][2];
			saveData.sort=propertiesData[2][2];
			SaveModbusProtocolReportInstanceData(saveData);
		}
	}
};



function SaveModbusProtocolReportInstanceData(saveData){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolReportInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getStore().load();
            	
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			data: JSON.stringify(saveData),
        }
	});
}

function CreateProtocolReportInstancePropertiesInfoTable(data){
	var root=[];
	var unitList=[];
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getReportUnitList',
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
				item2.title=loginUserLanguageResource.reportUnit;
				item2.value=data.unitName;
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title=loginUserLanguageResource.sortNum;
				item3.value=data.sort;
				root.push(item3);
			}else if(data.classes==2){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.reportUnit;
				item1.value=data.text;
				root.push(item1);
			}
			
			if(protocolReportInstancePropertiesHandsontableHelper==null || protocolReportInstancePropertiesHandsontableHelper.hot==undefined){
				protocolReportInstancePropertiesHandsontableHelper = ProtocolReportInstancePropertiesHandsontableHelper.createNew("ModbusProtocolReportInstancePropertiesTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolReportInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolReportInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolReportInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolReportInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolReportInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolReportInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolReportInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			
        }
	});
};

var ProtocolReportInstancePropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolReportInstancePropertiesHandsontableHelper = {};
	        protocolReportInstancePropertiesHandsontableHelper.hot = '';
	        protocolReportInstancePropertiesHandsontableHelper.classes =null;
	        protocolReportInstancePropertiesHandsontableHelper.divid = divid;
	        protocolReportInstancePropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolReportInstancePropertiesHandsontableHelper.colHeaders=[];
	        protocolReportInstancePropertiesHandsontableHelper.columns=[];
	        protocolReportInstancePropertiesHandsontableHelper.AllData=[];
	        protocolReportInstancePropertiesHandsontableHelper.unitList=[];
	        
	        protocolReportInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolReportInstancePropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolReportInstancePropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolReportInstancePropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolReportInstancePropertiesHandsontableHelper.divid);
	        	protocolReportInstancePropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [1,4,7],
	                columns:protocolReportInstancePropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolReportInstancePropertiesHandsontableHelper.colHeaders,//显示列头
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
		                    if(protocolReportInstancePropertiesHandsontableHelper.classes===0 || protocolReportInstancePropertiesHandsontableHelper.classes===2){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolReportInstancePropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolReportInstancePropertiesHandsontableHelper);
			                    	}
			                    }else if(visualColIndex === 2 && visualRowIndex===2){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolReportInstancePropertiesHandsontableHelper);
			                    	}
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    		this.type = 'dropdown';
		                    		this.source = protocolReportInstancePropertiesHandsontableHelper.unitList;
			                    	
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }
		                    	
		                    	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
				                }else if(visualColIndex === 2 && visualRowIndex!=1){
				                	cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addCellStyle;
				                }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolReportInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && protocolReportInstancePropertiesHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolReportInstancePropertiesHandsontableHelper!=null
	                		&& protocolReportInstancePropertiesHandsontableHelper.hot!=''
	                		&& protocolReportInstancePropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolReportInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolReportInstancePropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolReportInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolReportInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolReportInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolReportInstancePropertiesHandsontableHelper;
	    }
};