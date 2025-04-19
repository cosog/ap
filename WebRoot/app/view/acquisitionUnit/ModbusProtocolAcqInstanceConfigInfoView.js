//var protocolInstanceConfigItemsHandsontableHelper=null;
//var protocolConfigInstancePropertiesHandsontableHelper=null;
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
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addAcqInstance,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolInstanceConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolAcqInstanceWindow");
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
        				var window = Ext.create("AP.view.acquisitionUnit.ImportAcqInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportAcqInstanceWinTabLabel_Id").setHtml(loginUserLanguageResource.targetType+":【<font color=red>"+selectedDeviceTypeName+"</font>】,"+loginUserLanguageResourceFirstLower.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportAcqInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportAcqInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
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
                    	layout: 'fit',
                    	title:loginUserLanguageResource.acqInstanceList,
//                    	autoScroll:true,
//                        scrollable: true,
                    	id:"ModbusProtocolInstanceConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:loginUserLanguageResource.properties,
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ProtocolConfigInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolConfigInstancePropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolConfigInstancePropertiesHandsontableHelper!=null && protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolConfigInstancePropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
//                            		protocolConfigInstancePropertiesHandsontableHelper.hot.refreshDimensions();
                            	}
                            }
                        }
                    }]
                },{
                	border: true,
                	region: 'center',
                	title:loginUserLanguageResource.acqAndCtrlItemConfig,
                	id:"ModbusProtocolInstanceItemsTableTabPanel_Id",
                	layout: 'fit',
                    html:'<div class="ModbusProtocolInstanceItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolInstanceItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(protocolInstanceConfigItemsHandsontableHelper!=null && protocolInstanceConfigItemsHandsontableHelper.hot!=undefined){
//                        		protocolInstanceConfigItemsHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		protocolInstanceConfigItemsHandsontableHelper.hot.updateSettings({
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
function CreateProtocolInstanceConfigPropertiesInfoTable(data){
	var root=[];
	var srpAcqUnit=[];
	var pcpAcqUnit=[];
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getAcqUnitList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			srpAcqUnit=result.srpAcqUnit;
			pcpAcqUnit=result.pcpAcqUnit;
			
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
				item2.title=loginUserLanguageResource.acqUnit;
				item2.value=data.unitName;
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title=loginUserLanguageResource.acqProtocolType;
				item3.value=data.acqProtocolType;
				root.push(item3);
				
				var item4={};
				item4.id=4;
				item4.title=loginUserLanguageResource.ctrlProtocolType;
				item4.value=data.ctrlProtocolType;
				root.push(item4);
				
				
				var item5={};
				item5.id=5;
				item5.title=loginUserLanguageResource.signInPrefixSuffixHex;
				if(parseInt(data.signInPrefixSuffixHex)==1){
					item5.value=true;
				}else{
					item5.value=false;
				}
				root.push(item5);
				
				var item6={};
				item6.id=6;
				item6.title=loginUserLanguageResource.signInPrefix+'(HEX/ASC)';
				item6.value=data.signInPrefix;
				root.push(item6);
				
				var item7={};
				item7.id=7;
				item7.title=loginUserLanguageResource.signInSuffix+'(HEX/ASC)';
				item7.value=data.signInSuffix;
				root.push(item7);
				
				var item8={};
				item8.id=8;
				item8.title=loginUserLanguageResource.signInIDHex;
				if(parseInt(data.signInIDHex)==1){
					item8.value=true;
				}else{
					item8.value=false;
				}
				root.push(item8);
				
				var item9={};
				item9.id=9;
				item9.title=loginUserLanguageResource.heartbeatPrefixSuffixHex;
				if(parseInt(data.heartbeatPrefixSuffixHex)==1){
					item9.value=true;
				}else{
					item9.value=false;
				}
				root.push(item9);
				
				var item10={};
				item10.id=10;
				item10.title=loginUserLanguageResource.heartbeatPrefix+'(HEX/ASC)';
				item10.value=data.heartbeatPrefix;
				root.push(item10);
				
				var item11={};
				item11.id=11;
				item11.title=loginUserLanguageResource.heartbeatSuffix+'(HEX/ASC)';
				item11.value=data.heartbeatSuffix;
				root.push(item11);
				
				
				var item12={};
				item12.id=12;
				item12.title=loginUserLanguageResource.packetSendInterval+'(ms)';
				item12.value=data.packetSendInterval;
				root.push(item12);
				
				var item13={};
				item13.id=13;
				item13.title=loginUserLanguageResource.sortNum;
				item13.value=data.sort;
				root.push(item13);
			}else if(data.classes==2){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.acqUnit;
				item1.value=data.text;
				root.push(item1);
			}
			
			if(protocolConfigInstancePropertiesHandsontableHelper==null || protocolConfigInstancePropertiesHandsontableHelper.hot==undefined){
				protocolConfigInstancePropertiesHandsontableHelper = ProtocolConfigInstancePropertiesHandsontableHelper.createNew("ProtocolConfigInstancePropertiesTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolConfigInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolConfigInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolConfigInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolConfigInstancePropertiesHandsontableHelper.srpAcqUnit=srpAcqUnit;
		        protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit=pcpAcqUnit;
				protocolConfigInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolConfigInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolConfigInstancePropertiesHandsontableHelper.srpAcqUnit=srpAcqUnit;
		        protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit=pcpAcqUnit;
				protocolConfigInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
		},
		failure:function(){
//			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
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
	        protocolConfigInstancePropertiesHandsontableHelper.srpAcqUnit=[];
	        protocolConfigInstancePropertiesHandsontableHelper.pcpAcqUnit=[];
	        
	        protocolConfigInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolConfigInstancePropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
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
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if (visualColIndex ==0 || visualColIndex ==1) {
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
			                }
		                    if(protocolConfigInstancePropertiesHandsontableHelper.classes===0 || protocolConfigInstancePropertiesHandsontableHelper.classes===2){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolConfigInstancePropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===2) {
			                    	this.type = 'dropdown';
			                    	this.source = ['modbus-tcp','modbus-rtu','private-srp','private-mqtt','private-kd93','private-lq1000'];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if (visualColIndex === 2 && visualRowIndex===3) {
			                    	this.type = 'dropdown';
			                    	this.source = ['modbus-tcp','modbus-rtu','private-srp','private-mqtt'];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if (visualColIndex === 2 && (visualRowIndex===4 ||visualRowIndex===7 || visualRowIndex===8) ) {
			                    	this.type = 'checkbox';
			                    }else if(visualColIndex === 2 && (visualRowIndex===11 || visualRowIndex===12)){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && (visualRowIndex===5 || visualRowIndex===6) ) {
			                    	if(protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined && protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
			                    		var signInPrefixSuffixHex=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(4,2);
			                    		if(signInPrefixSuffixHex){
			                    			this.validator=function (val, callback) {
					                    	    return handsontableDataCheck_HexStr_Nullable(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
					                    	}
			                    		}else{
			                    			this.validator=function (val, callback) {
			                    				return callback(true);
			                    			}
			                    		}
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && (visualRowIndex===9 || visualRowIndex===10) ) {
			                    	if(protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined && protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
			                    		var heartbeatPrefixSuffixHex=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(8,2);
			                    		if(heartbeatPrefixSuffixHex){
			                    			this.validator=function (val, callback) {
					                    	    return handsontableDataCheck_HexStr_Nullable(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
					                    	}
			                    		}else{
			                    			this.validator=function (val, callback) {
			                    				return callback(true);
			                    			}
			                    		}
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if((coords.col<2||(coords.col==2&&coords.row!=4&&coords.row!=7&&coords.row!=8  ))
	                		&& protocolConfigInstancePropertiesHandsontableHelper!=null
	                		&& protocolConfigInstancePropertiesHandsontableHelper.hot!=''
	                		&& protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolConfigInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigInstancePropertiesHandsontableHelper;
	    }
};

function CreateProtocolInstanceAcqItemsInfoTable(id,instanceName,classes){
	Ext.getCmp("ModbusProtocolInstanceItemsTableTabPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolInstanceItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolInstanceItemsTableTabPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolInstanceItemsTableTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.acqAndCtrlItemConfig);
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolInstanceConfigItemsHandsontableHelper==null || protocolInstanceConfigItemsHandsontableHelper.hot==undefined){
				protocolInstanceConfigItemsHandsontableHelper = ProtocolInstanceConfigItemsHandsontableHelper.createNew("ModbusProtocolInstanceItemsConfigTableInfoDiv_id");

				var colHeaders="[" 
					+"['','',{label: '"+loginUserLanguageResource.lowerComputer+"', colspan: 5},{label: '"+loginUserLanguageResource.upperComputer+"', colspan: 5}]," 
					+"['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.startAddress+"','"+loginUserLanguageResource.storeDataType+"','"+loginUserLanguageResource.quantity+"','"+loginUserLanguageResource.RWType+"','"+loginUserLanguageResource.acqMode+"','"+loginUserLanguageResource.IFDataType+"','"+loginUserLanguageResource.prec+"','"+loginUserLanguageResource.ratio+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.resolutionMode+"']" 
					+"]";
				
				var columns="[{data:'id'},{data:'title'},"
				 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}},"
				 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
				 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}}," 
				 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.readOnly+"', '"+loginUserLanguageResource.writeOnly+"', '"+loginUserLanguageResource.readWrite+"']}," 
				 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.activeAcqModel+"', '"+loginUserLanguageResource.passiveAcqModel+"']}," 
					+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
					+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}}," 
					+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolInstanceConfigItemsHandsontableHelper);}}," 
					+"{data:'unit'}," 
					+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}" 
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
			Ext.getCmp("ModbusProtocolInstanceItemsTableTabPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
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
	        
	        protocolInstanceConfigItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolInstanceConfigItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolInstanceConfigItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolInstanceConfigItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolInstanceConfigItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolInstanceConfigItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolInstanceConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolInstanceConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolInstanceConfigItemsHandsontableHelper.divid);
	        	protocolInstanceConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,130,80,90,90,80,80,90,80,80,80,80],
	                columns:protocolInstanceConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:protocolInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    cellProperties.renderer=protocolInstanceConfigItemsHandsontableHelper.addReadOnlyBg;
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolInstanceConfigItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolInstanceConfigItemsHandsontableHelper!=null
	                		&& protocolInstanceConfigItemsHandsontableHelper.hot!=''
	                		&& protocolInstanceConfigItemsHandsontableHelper.hot!=undefined 
	                		&& protocolInstanceConfigItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolInstanceConfigItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
			saveData.unitId=selectedItem.data.unitId;
			saveData.unitName=propertiesData[1][2];
			saveData.acqProtocolType=propertiesData[2][2];
			saveData.ctrlProtocolType=propertiesData[3][2];
			
			if(propertiesData[4][2]==true){
				saveData.signInPrefixSuffixHex=1;
			}else{
				saveData.signInPrefixSuffixHex=0;
			}
			saveData.signInPrefix=propertiesData[5][2];
			saveData.signInSuffix=propertiesData[6][2];
			if(propertiesData[7][2]==true){
				saveData.signInIDHex=1;
			}else{
				saveData.signInIDHex=0;
			}
			
			if(propertiesData[8][2]==true){
				saveData.heartbeatPrefixSuffixHex=1;
			}else{
				saveData.heartbeatPrefixSuffixHex=0;
			}
			saveData.heartbeatPrefix=propertiesData[9][2];
			saveData.heartbeatSuffix=propertiesData[10][2];
			
			saveData.packetSendInterval=propertiesData[11][2];
			
			saveData.sort=propertiesData[12][2];
			
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
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id").getStore().load();
            	
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

