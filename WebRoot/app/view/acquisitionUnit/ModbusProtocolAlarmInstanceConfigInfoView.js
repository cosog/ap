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
                    id: 'ModbusProtocolAlarmInstanceProtocolSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolAlarmInstanceTreeSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden: false,
                    handler: function (v, o) {
                		var treePanel=Ext.getCmp("AlarmInstanceProtocolTreeGridPanel_Id");
                		if(isNotVal(treePanel)){
                			treePanel.getStore().load();
                		}else{
                			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceProtocolTreeInfoStore');
                		}
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addAcqInstance,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolAlarmInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAlarmInstanceConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolAlarmInstanceWindow");
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
        				var window = Ext.create("AP.view.acquisitionUnit.ImportAlarmInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportAlarmInstanceWinTabLabel_Id").setHtml(loginUserLanguageResource.targetType+":【<font color=red>"+selectedDeviceTypeName+"</font>】,"+loginUserLanguageResourceFirstLower.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportAlarmInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportAlarmInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'20%',
                    layout: "fit",
                    id:'ModbusProtocolAlarmInstanceProtocolListPanel_Id',
                    border: false,
                    title:loginUserLanguageResource.protocolList,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left'
                },{
                	region: 'center',
                	title:loginUserLanguageResource.alarmInstanceList,
                	layout: 'fit',
                	id:"ModbusProtocolAlarmInstanceConfigPanel_Id"
                },{
                	region: 'east',
                	width:'50%',
                	title:loginUserLanguageResource.properties,
                	collapsible: true,
                    split: true,
                	layout: 'fit',
                    html:'<div class="ProtocolAlarmInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolAlarmInstancePropertiesTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(protocolAlarmInstancePropertiesHandsontableHelper!=null && protocolAlarmInstancePropertiesHandsontableHelper.hot!=undefined){
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
            }]
    	});
        this.callParent(arguments);
    }
});
function CreateProtocolAlarmInstancePropertiesInfoTable(data){
	var root=[];
	var unitList=[];
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getAlarmUnitList',
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
				item2.title=loginUserLanguageResource.alarmUnit;
				item2.value=data.alarmUnitName;
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title=loginUserLanguageResource.sortNum;
				item3.value=data.sort;
				root.push(item3);
			}else if(data.classes==2){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.alarmUnit;
				item1.value=data.text;
				root.push(item1);
			}
			
			if(protocolAlarmInstancePropertiesHandsontableHelper==null || protocolAlarmInstancePropertiesHandsontableHelper.hot==undefined){
				protocolAlarmInstancePropertiesHandsontableHelper = ProtocolAlarmInstancePropertiesHandsontableHelper.createNew("ProtocolAlarmInstancePropertiesTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolAlarmInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolAlarmInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolAlarmInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolAlarmInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolAlarmInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolAlarmInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolAlarmInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			
        }
	});
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
	        protocolAlarmInstancePropertiesHandsontableHelper.unitList=[];
	        
	        protocolAlarmInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
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
	        		colWidths: [1,4,5],
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
			                    	cellProperties.renderer = protocolAlarmInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if(visualColIndex === 2 && visualRowIndex===2){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolAlarmInstancePropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolAlarmInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    		this.type = 'dropdown';
		                    		this.source = protocolAlarmInstancePropertiesHandsontableHelper.unitList;
			                    	
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if (visualColIndex ==0 || visualColIndex ==1) {
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
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id").getStore().load();
            	
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
