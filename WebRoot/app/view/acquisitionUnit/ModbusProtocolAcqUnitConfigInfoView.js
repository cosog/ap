//var protocolAcqUnitConfigItemsHandsontableHelper=null;
//var protocolConfigAcqUnitPropertiesHandsontableHelper=null;
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
                    text: loginUserLanguageResource.refresh,
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
                    text: loginUserLanguageResource.addAcqUnit,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addAcquisitionUnitInfo();
        			}
        		},"-",{
        			xtype: 'button',
                    text: loginUserLanguageResource.addAcqGroup,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addAcquisitionGroupInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAcqUnitConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolAcqUnitWindow");
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
        				var window = Ext.create("AP.view.acquisitionUnit.ImportAcqUnitWindow");
                        window.show();
        				Ext.getCmp("ImportAcqUnitWinTabLabel_Id").setHtml(loginUserLanguageResource.targetType+":【<font color=red>"+selectedDeviceTypeName+"</font>】,"+loginUserLanguageResourceFirstLower.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportAcqUnitWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportAcqUnitWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'25%',
                    layout: "border",
                    border: true,
                    header: false,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:loginUserLanguageResource.acqUnitConfig,
                    	layout: 'fit',
                    	id:"ModbusProtocolAcqGroupConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:loginUserLanguageResource.properties,
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolAcqGroupPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAcqGroupPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolConfigAcqUnitPropertiesHandsontableHelper!=null && protocolConfigAcqUnitPropertiesHandsontableHelper.hot!=undefined){
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
                	region: 'center',
                	tbar:[{
                        xtype: 'button',
                        text: loginUserLanguageResource.selectAll,
                        id: 'acqUnitConfigSelectAllBtn',
                        disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	if(protocolAcqUnitConfigItemsHandsontableHelper!=undefined && protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
                        		var rowCount = protocolAcqUnitConfigItemsHandsontableHelper.hot.countRows();
                            	var updateData=[];
                            	var selected=true;
                                for(var i=0;i<rowCount;i++){
                                	var data=[i,'checked',selected];
                                	updateData.push(data);
                                }
                                protocolAcqUnitConfigItemsHandsontableHelper.hot.setDataAtRowProp(updateData);
                        	}
                        }
                    },{
                        xtype: 'button',
                        text: loginUserLanguageResource.deselectAll,
                        id: 'acqUnitConfigDeselectAllBtn',
                        disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	if(protocolAcqUnitConfigItemsHandsontableHelper!=undefined && protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
                        		var rowCount = protocolAcqUnitConfigItemsHandsontableHelper.hot.countRows();
                            	var updateData=[];
                            	var selected=false;
                                for(var i=0;i<rowCount;i++){
                                	var data=[i,'checked',selected];
                                	updateData.push(data);
                                }
                                protocolAcqUnitConfigItemsHandsontableHelper.hot.setDataAtRowProp(updateData);
                        	}
                        }
                    }],
                    title:loginUserLanguageResource.acqAndCtrlItemConfig,
                    id:"ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id",
                    layout: 'fit',
                    html:'<div class="ModbusProtocolAcqGroupItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAcqGroupItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(protocolAcqUnitConfigItemsHandsontableHelper!=null && protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
//                        		protocolAcqUnitConfigItemsHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height-22-1;//减去tbar
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
	Ext.getCmp("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	if(protocolAcqUnitConfigItemsHandsontableHelper!=null){
		if(protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
			protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
		}
		protocolAcqUnitConfigItemsHandsontableHelper=null;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolAcqUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAcqUnitConfigItemsHandsontableHelper==null || protocolAcqUnitConfigItemsHandsontableHelper.hot==undefined){
				protocolAcqUnitConfigItemsHandsontableHelper = ProtocolAcqUnitConfigItemsHandsontableHelper.createNew("ModbusProtocolAcqGroupItemsConfigTableInfoDiv_id");
				var colHeaders="['','"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.startAddress+"','"+loginUserLanguageResource.RWType+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.resolutionMode+"','','"+loginUserLanguageResource.dailyCalculate+"','"+loginUserLanguageResource.dailyCalculateColumn+"']";
				var columns="[" 
						+"{data:'checked',type:'checkbox'}," 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAcqUnitConfigItemsHandsontableHelper);}},"
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.readOnly+"', '"+loginUserLanguageResource.readWrite+"']}," 
						+"{data:'unit'},"
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}," 
						+"{data:'bitIndex'}," 
						+"{data:'dailyTotalCalculate',type:'checkbox'},"
						+"{data:'dailyTotalCalculateName'}"
						+"]";
				protocolAcqUnitConfigItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAcqUnitConfigItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				
				if(classes==3 && type==0){
					protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns=[7];
				}else{
					protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns=[7,8,9];
				}
				
				protocolAcqUnitConfigItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolAcqUnitConfigItemsHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
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
	        protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns=[];
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAcqUnitConfigItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAcqUnitConfigItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAcqUnitConfigItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAcqUnitConfigItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAcqUnitConfigItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAcqUnitConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAcqUnitConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAcqUnitConfigItemsHandsontableHelper.divid);
	        	protocolAcqUnitConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,50,140,60,80,80,80,80,80,80],
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
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue();
		                	if(ScadaDriverModbusConfigSelectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAcqUnitConfigItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex<=6) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAcqUnitConfigItemsHandsontableHelper.addReadOnlyBg;
		    		                }else if(visualColIndex!=10 && visualColIndex!=12){
		                				if(protocolAcqUnitConfigItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		                    	&& protocolAcqUnitConfigItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    	                    		cellProperties.renderer = protocolAcqUnitConfigItemsHandsontableHelper.addCellStyle;
		    	                    	}
		                			}
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAcqUnitConfigItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col!=0 && coords.col!=7 && protocolAcqUnitConfigItemsHandsontableHelper!=null&&protocolAcqUnitConfigItemsHandsontableHelper.hot!=''&&protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined && protocolAcqUnitConfigItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAcqUnitConfigItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolAcqUnitConfigItemsHandsontableHelper.saveData = function () {}
	        protocolAcqUnitConfigItemsHandsontableHelper.clearContainer = function () {
	        	protocolAcqUnitConfigItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAcqUnitConfigItemsHandsontableHelper;
	    }
};


function CreateProtocolAcqUnitConfigPropertiesInfoTable(data){
	var root=[];
	
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.rootNode;
		item1.value=loginUserLanguageResource.unitList;
		root.push(item1);
	}else if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.protocolName;
		item1.value=data.text;
		root.push(item1);
	}else if(data.classes==2){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.unitName;
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title=loginUserLanguageResource.remark;
		item2.value=data.remark;
		root.push(item2);
	}else if(data.classes==3){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.groupName;
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title=loginUserLanguageResource.groupType;
		item2.value=data.typeName;
		root.push(item2);
		
		if(data.type==0){
			var item3={};
			item3.id=3;
			item3.title=loginUserLanguageResource.groupTimingInterval+'(s)';
			item3.value=data.groupTimingInterval;
			root.push(item3);
			
			var item4={};
			item4.id=4;
			item4.title=loginUserLanguageResource.groupSavingInterval+'(s)';
			item4.value=data.groupSavingInterval;
			root.push(item4);
			
			var item5={};
			item5.id=5;
			item5.title=loginUserLanguageResource.remark;
			item5.value=data.remark;
			root.push(item5);
		}else if(data.type==1){
			var item3={};
			item3.id=3;
			item3.title=loginUserLanguageResource.remark;
			item3.value=data.remark;
			root.push(item3);
		}
		
	}
	
	if(protocolConfigAcqUnitPropertiesHandsontableHelper==null || protocolConfigAcqUnitPropertiesHandsontableHelper.hot==undefined){
		protocolConfigAcqUnitPropertiesHandsontableHelper = ProtocolConfigAcqUnitPropertiesHandsontableHelper.createNew("ModbusProtocolAcqGroupPropertiesTableInfoDiv_id");
		var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
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
	        
	        protocolConfigAcqUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolConfigAcqUnitPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
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
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if (visualColIndex ==0 || visualColIndex ==1) {
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigAcqUnitPropertiesHandsontableHelper.addBoldBg;
			                }
		                    if(protocolConfigAcqUnitPropertiesHandsontableHelper.classes===0 || protocolConfigAcqUnitPropertiesHandsontableHelper.classes===1){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigAcqUnitPropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolConfigAcqUnitPropertiesHandsontableHelper.classes===2){
		                    	if (visualColIndex === 2 && visualRowIndex===0) {
		                    		this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigAcqUnitPropertiesHandsontableHelper);
			                    	}
			                    }
		                    	if (visualColIndex === 2) {
	                    			cellProperties.renderer = protocolConfigAcqUnitPropertiesHandsontableHelper.addCellStyle;
	                    		}
		                    }else if(protocolConfigAcqUnitPropertiesHandsontableHelper.classes===3){
		                    	if (visualColIndex === 2 && visualRowIndex===0) {
		                    		this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigAcqUnitPropertiesHandsontableHelper);
			                    	}
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
			                    	this.type = 'dropdown';
			                    	this.source = [loginUserLanguageResource.acqGroup,loginUserLanguageResource.controlGroup];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if(visualColIndex === 2 && (visualRowIndex===2||visualRowIndex===3) && protocolConfigAcqUnitPropertiesHandsontableHelper.type==0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolConfigAcqUnitPropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolConfigAcqUnitPropertiesHandsontableHelper.addCellStyle;
			                    }
		                    }
	                    }else{
	                    	cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigAcqUnitPropertiesHandsontableHelper.addBoldBg;
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolConfigAcqUnitPropertiesHandsontableHelper!=null&&protocolConfigAcqUnitPropertiesHandsontableHelper.hot!=''&&protocolConfigAcqUnitPropertiesHandsontableHelper.hot!=undefined && protocolConfigAcqUnitPropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolConfigAcqUnitPropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
			var groupType=0;
			if(protocolProperties.typeName==loginUserLanguageResource.controlGroup){
				groupType=1;
			}
			grantAcquisitionItemsPermission(groupType);
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
            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
            	Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
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
            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
            	if(acqUnitSaveData.delidslist!=undefined && acqUnitSaveData.delidslist.length>0){//如果删除
            		Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(0);
            	}
            	Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
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
            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
            	if(acqGroupSaveData.delidslist!=undefined && acqGroupSaveData.delidslist.length>0){//如果删除
            		Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(0);
            	}
            	Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
        	data: JSON.stringify(acqGroupSaveData),
        	protocol: protocol,
        	unitId: unitId
        }
	});
};
